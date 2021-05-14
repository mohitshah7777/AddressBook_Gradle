package com.bridgelabz.addressbook;

import java.util.List;

public class AddressBookData {
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public int zip;
    public String phoneNumber;
    public String email;

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

    @Override
    public String toString(){
        return "Address Book Data"+"(firstname = "+firstName+" lastname = "+lastName+" address = "+address+" city = "+
                city+" state = "+state+" zip = "+zip+" phone number = "+phoneNumber+" email = "+email+")";
    }
}
