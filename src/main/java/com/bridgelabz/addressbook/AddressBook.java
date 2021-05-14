package com.bridgelabz.addressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBook {

    public static final String CSV_FILE_PATH = "address-book.csv";
    public static final String JSON_FILE_PATH = "address-book.json";

    public enum IOService {
        CONSOLE_IO, FILE_IO, CSV_FILE, JSON_FILE, DB_IO
    }
    private List<AddressBookData> addressBookDataList;
    private AddressBookDB addressBookDB;

    public AddressBook(){
        addressBookDB = AddressBookDB.getInstance();
    }

    public AddressBook(List<AddressBookData> addressBookDataList) {
        this.addressBookDataList = addressBookDataList;
    }

    public void readAddressBookData(Scanner consoleInputReader) {
        System.out.println("Enter name");
        String name = consoleInputReader.next();
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
        System.out.println("Enter type");
        Integer type = consoleInputReader.nextInt();
        addressBookDataList.add(new AddressBookData(type,name,firstName,lastName,address,city,state,zip,phoneNumber,email));
    }

    //UC-13
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

    //UC-15
    public boolean writeJSONAddressBookData(IOService ioService) {
        if (ioService.equals(IOService.CONSOLE_IO))
            System.out.println("\nWriting Address Book Roaster to Console\n" + addressBookDataList);
        else
            new AddressBookJSON().writeData(addressBookDataList);
            return true;
    }

    //UC-13
    public long readAddressBookData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            this.addressBookDataList = new AddressBookFileIO().readData();
        return addressBookDataList.size();
    }

    //UC-14
    public int readCSVAddressBookData(IOService ioService) throws ContactException {
        if (ioService.equals(IOService.CONSOLE_IO))
            new AddressBookCSV().readData(CSV_FILE_PATH);
        return AddressBookCSV.readData(CSV_FILE_PATH);
    }

    //UC-15
    public void readJSONAddressBookData(IOService ioService) {
        if (ioService.equals(IOService.CONSOLE_IO))
            new AddressBookJSON().readData(JSON_FILE_PATH);
        AddressBookJSON.readData(JSON_FILE_PATH);
    }

    //UC-16
    public List<AddressBookData> readAddressBookDataDB(IOService ioService){
        if(ioService.equals(IOService.DB_IO))
            this.addressBookDataList = addressBookDB.readData();
        return this.addressBookDataList;
    }

    //UC-17
    public void updateAddressBook(String name, String phoneNumber) {
        int result = addressBookDB.updateAddressBookData(name,phoneNumber);
        if(result == 0)
            return;
        AddressBookData addressBookData = this.getAddressBookData(name);
        if(addressBookData != null)
            addressBookData.phoneNumber = phoneNumber;
    }

    private AddressBookData getAddressBookData(String name) {
        return this.addressBookDataList.stream()
                .filter(addressBookDataListItem -> addressBookDataListItem.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    public boolean checkAddressBookInSyncWithDB(String name) {
        List<AddressBookData> addressBookDataList = addressBookDB.getAddressBookData(name);
        return addressBookDataList.get(0).equals(getAddressBookData(name));
    }
    //END OF UC-17

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
