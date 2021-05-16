package com.bridgelabz.addressbook;

import java.util.Date;
import java.util.Objects;

public class AddressBookData {
    public String name;
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public int zip;
    public String phoneNumber;
    public String email;
    public int type;
    public Date date;

    public AddressBookData(int type,String name,String firstName, String lastName, String address, String city, String state, int zip, String phoneNumber, String email) {
        this.type=type;
        this.name=name;
        this.firstName=firstName;
        this.lastName=lastName;
        this.address=address;
        this.city=city;
        this.state=state;
        this.zip=zip;
        this.phoneNumber=phoneNumber;
        this.email=email;
        }

    public AddressBookData(String firstName, String lastName, String address, String city, String state, int zip, String phoneNumber, String email) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.address=address;
        this.city=city;
        this.state=state;
        this.zip=zip;
        this.phoneNumber=phoneNumber;
        this.email=email;
    }

    public AddressBookData(int type, String name, String firstName, String lastName, String address, String city, String state, int zip, String phoneNumber, String email, java.sql.Date date) {
        this.type=type;
        this.name=name;
        this.firstName=firstName;
        this.lastName=lastName;
        this.address=address;
        this.city=city;
        this.state=state;
        this.zip=zip;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.date=date;
    }

    @Override
    public String toString(){
        return "Address Book Data"+"(name "+name + " firstname = "+firstName+" lastname = "+lastName+" address = "+address+" city = "+
                city+" state = "+state+" zip = "+zip+" phone number = "+phoneNumber+" email = "+email+" type"+type+"+ Date"+date+")\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressBookData addressBookData = (AddressBookData) o;
        return zip == addressBookData.zip && Objects.equals(name, addressBookData.name) &&
                Objects.equals(firstName, addressBookData.firstName) &&
                Objects.equals(lastName, addressBookData.lastName) &&
                Objects.equals(address, addressBookData.address) &&
                Objects.equals(city, addressBookData.city) &&
                Objects.equals(state, addressBookData.state) &&
                Objects.equals(phoneNumber, addressBookData.phoneNumber) &&
                Objects.equals(email, addressBookData.email) &&
                Objects.equals(type, addressBookData.type) &&
                Objects.equals(date,addressBookData.date);
    }
}
