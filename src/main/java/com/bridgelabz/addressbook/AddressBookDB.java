package com.bridgelabz.addressbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDB {

    public List<AddressBookData> readData() {
        String sql = "SELECT * FROM address_book_table;";
        List<AddressBookData> addressBookDataList = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Integer type = resultSet.getInt("type");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                Integer zip = resultSet.getInt("zip");
                String phoneNumber = resultSet.getString("phoneNumber");
                String email = resultSet.getString("email");
                addressBookDataList.add(new AddressBookData(type,firstName,lastName,address,city,state,zip,phoneNumber,email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookDataList;
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
    }}
