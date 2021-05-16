package com.bridgelabz.addressbook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookDB {

    private static AddressBookDB addressBookDB;
    private PreparedStatement addressBookDataStatement;
    private AddressBookDB(){
    }

    public static AddressBookDB getInstance(){
        if(addressBookDB == null)
            addressBookDB = new AddressBookDB();
        return addressBookDB;
    }

    //UC-16
    public List<AddressBookData> readData() {
        String sql = "SELECT * from address_book_table;";
        return this.getContactDetailsUsingSqlQuery(sql);
    }

    //UC-17
    int updateAddressBookData(String name, String phoneNumber) {
        return this.updateAddressBookDataUsingPreparedStatement(name,phoneNumber);
    }

    //UC-18
    public List<AddressBookData> getContactForGivenDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = String.format(
                "SELECT * from address_book_table WHERE date_added BETWEEN '%s' AND '%s';",
                Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getContactDetailsUsingSqlQuery(sql);
    }

    //UC19
    public Map<String, Integer> getContactsByCityOrState() {
        Map<String, Integer> contactByCityOrStateMap = new HashMap<>();
        ResultSet resultSet;
        String sqlCity = "SELECT city, count(firstName) as count from address_book_table group by city; ";
        String sqlState = "SELECT state, count(firstName) as count from address_book_table group by state; ";
        try (Connection connection = addressBookDB.getConnection()) {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlCity);
            while (resultSet.next()) {
                String city = resultSet.getString("city");
                Integer count = resultSet.getInt("count");
                contactByCityOrStateMap.put(city,count);
            }
            resultSet = statement.executeQuery(sqlState);
            while (resultSet.next()) {
                String state = resultSet.getString("state");
                Integer count = resultSet.getInt("count");
                contactByCityOrStateMap.put(state,count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactByCityOrStateMap;
    }

    private List<AddressBookData> getContactDetailsUsingSqlQuery(String sql) {
        List<AddressBookData> addressBookDataList = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            addressBookDataList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;
    }

    private List<AddressBookData> getAddressBookData(ResultSet resultSet) {
        List<AddressBookData> addressBookDataList = new ArrayList<>();
        try{
            while (resultSet.next()){
                Integer type = resultSet.getInt("type");
                String name = resultSet.getString("name");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                Integer zip = resultSet.getInt("zip");
                String phoneNumber = resultSet.getString("phoneNumber");
                String email = resultSet.getString("email");
                addressBookDataList.add(new AddressBookData(type,name,firstName,lastName,address,city,state,zip,phoneNumber,email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;
    }

    public List<AddressBookData> getAddressBookData(String name) {
        List<AddressBookData> addressBookDataList = null;
        if(this.addressBookDataStatement == null)
            this.prepareStatementForAddressBookData();
        try{
            addressBookDataStatement.setString(1,name);
            ResultSet resultSet = addressBookDataStatement.executeQuery();
            addressBookDataList = this.getAddressBookData(resultSet);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return addressBookDataList;
    }

    private void prepareStatementForAddressBookData() {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM address_book_table WHERE name = ?";
            addressBookDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int updateAddressBookDataUsingPreparedStatement(String name, String phoneNumber) {
        try(Connection connection = this.getConnection()){
            String sql = String.format("UPDATE address_book_table SET phoneNumber = ? WHERE name = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,phoneNumber);
            preparedStatement.setString(2,name);
            return preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
        String userName = "root";
        String passWord = "12345";
        Connection connection;
        System.out.println("Connecting to database"+jdbcURL);
        connection = DriverManager.getConnection(jdbcURL,userName,passWord);
        System.out.println("Connection is successful!!"+connection);
        return connection;
    }
}
