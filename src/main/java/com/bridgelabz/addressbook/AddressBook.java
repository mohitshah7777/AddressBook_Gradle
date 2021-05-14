package com.bridgelabz.addressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBook {

    public static final String CSV_FILE_PATH = "address-book.csv";
    public enum IOService {
        CONSOLE_IO, FILE_IO, CSV_FILE
    }

    private List<AddressBookData> addressBookDataList;

    public AddressBook(List<AddressBookData> addressBookDataList) {
        this.addressBookDataList = addressBookDataList;
    }
    public AddressBook(){}

    public void readAddressBookData(Scanner consoleInputReader) {
        System.out.println("Enter firstname");
        String firstName = consoleInputReader.next();
        System.out.println("Enter lastname");
        String lastName = consoleInputReader.next();
        System.out.println("Enter address");
        String address = consoleInputReader.next();
        System.out.println("Enter city");
        String city = consoleInputReader.next();
        System.out.println("Enter state");
        String state = consoleInputReader.next();
        System.out.println("Enter zip");
        int zip = consoleInputReader.nextInt();
        System.out.println("Enter phone number");
        String phoneNumber = consoleInputReader.next();
        System.out.println("Enter email");
        String email = consoleInputReader.next();
        addressBookDataList.add(new AddressBookData(firstName,lastName,address,city,state,zip,phoneNumber,email));
    }

    public void writeAddressBookData(IOService ioService) {
        if (ioService.equals(IOService.CONSOLE_IO))
            System.out.println("\nWriting Address Book Roaster to Console\n" + addressBookDataList);
        else
            new AddressBookFileIO().writeData(addressBookDataList);
    }

    //UC-14
    public void writeCSVAddressBookData(IOService ioService) {
        if (ioService.equals(IOService.CONSOLE_IO))
            System.out.println("\nWriting Address Book Roaster to Console\n" + addressBookDataList);
        else
            new AddressBookCSV().writeCSVData();
    }

    public long readAddressBookData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            this.addressBookDataList = new AddressBookFileIO().readData();
        return addressBookDataList.size();
    }

    //UC 14
    public int readCSVAddressBookData(IOService ioService) throws ContactException {
        if (ioService.equals(IOService.CONSOLE_IO))
            new AddressBookCSV().readData(CSV_FILE_PATH);
        return AddressBookCSV.readData(CSV_FILE_PATH);
    }

    public long countEntries(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            return new AddressBookFileIO().countEntries();
        return 0;
    }

    public void printData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            new AddressBookFileIO().printData();
    }

    public static void main(String[] args) {
        ArrayList<AddressBookData> addressBookDataList = new ArrayList<>();
        AddressBook addressBook = new AddressBook(addressBookDataList);
        Scanner consoleInputReader = new Scanner(System.in);
        addressBook.readAddressBookData(consoleInputReader);
        addressBook.writeAddressBookData(IOService.CONSOLE_IO);
    }
}
