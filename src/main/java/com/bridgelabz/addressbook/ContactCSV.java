package com.bridgelabz.addressbook;
import com.opencsv.bean.CsvBindByName;

public class ContactCSV {
    @CsvBindByName(column = "firstname", required = true)
    public String firstName;

    @CsvBindByName(column = "lastname", required = true)
    public String lastName;

    @CsvBindByName(column = "address", required = true)
    public String address;

    @CsvBindByName(column = "city", required = true)
    public String city;

    @CsvBindByName(column = "state", required = true)
    public String state;

    @CsvBindByName(column = "zip", required = true)
    public int zip;

    @CsvBindByName(column = "phoneNumber", required = true)
    public String phoneNumber;

    @CsvBindByName(column = "email", required = true)
    public String email;

    @Override
    public String toString() {
        return "ContactCSV{" +
                "firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
