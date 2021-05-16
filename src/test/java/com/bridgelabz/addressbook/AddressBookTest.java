package com.bridgelabz.addressbook;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bridgelabz.addressbook.AddressBook.IOService.*;

public class AddressBookTest {

    //UC-13
    @Test
    public void givenContactWhenWrittenToFile_shouldMatchContactEntries() {
        AddressBookData[] addressBookData = {
                new AddressBookData("Virat","Kohli","Saket vihar","Delhi","Delhi",400112,"7788554466","viratkohli@gmail.com"),
                new AddressBookData("Rohit","Sharma","Bandra","Mumbai","MH",411112,"7788112233","rohitsharma@gmail.com"),
                new AddressBookData("Ravindra","Jadeja","Kalvad Road","Jamnagar","Gujarat",423568,"9988776655","ravindrajadeja@gmail.com"),
        };
        AddressBook addressBook;
        addressBook = new AddressBook(Arrays.asList(addressBookData));
        addressBook.writeAddressBookData(FILE_IO);
        System.out.println("Reading from file -");
        addressBook.readAddressBookData(FILE_IO);
        long entries = addressBook.countEntries(FILE_IO);
        addressBook.printData(FILE_IO);
        Assert.assertEquals(3,entries);
    }

    //UC-14
    @Test
    public void givenContactWhenReadFromCSVFile_shouldMatchWithFile() throws ContactException {
        AddressBook addressBook;
        addressBook = new AddressBook();
        addressBook.writeCSVAddressBookData(CSV_FILE);
        int entries = addressBook.readCSVAddressBookData(CSV_FILE);
        Assert.assertEquals(3,entries);
    }

    //UC-15
    @Test
    public void givenContactWhenReadFromJSONFile_shouldMatchWithFile()  {
        AddressBookData[] addressBookData  = {
                new AddressBookData("Virat","Kohli","Saket vihar","Delhi","Delhi",400112,"7788554466","viratkohli@gmail.com"),
                new AddressBookData("Rohit","Sharma","Bandra","Mumbai","MH",411112,"7788112233","rohitsharma@gmail.com"),
                new AddressBookData("Ravindra","Jadeja","Kalvad Road","Jamnagar","Gujarat",423568,"9988776655","ravindrajadeja@gmail.com"),
        };
        AddressBook addressBook;
        addressBook = new AddressBook(Arrays.asList(addressBookData));
        addressBook.writeJSONAddressBookData(JSON_FILE);
        addressBook.readJSONAddressBookData(JSON_FILE);
        boolean result = addressBook.writeJSONAddressBookData(JSON_FILE);
        Assert.assertTrue(result);
    }

    //UC-16
    @Test
    public void contactsWhenRetrievedFromDB_ShouldMatchCount() {
        AddressBook addressBook = new AddressBook();
        List<AddressBookData> addressBookData = addressBook.readAddressBookDataDB(DB_IO);
        Assert.assertEquals(5, addressBookData.size());
    }

    //UC-17
    @Test
    public void givenNewPhoneNumberForPerson_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB() {
        AddressBook addressBook = new AddressBook();
        addressBook.readAddressBookDataDB(DB_IO);
        addressBook.updateAddressBook("Mohit Shah", "456");
        boolean result = addressBook.checkAddressBookInSyncWithDB("Mohit Shah");
        Assert.assertTrue(result);
    }

    //UC-18
    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
        AddressBook addressBook = new AddressBook();
        addressBook.readAddressBookDataDB(DB_IO);
        LocalDate startDate = LocalDate.of(2019, 01, 1);
        LocalDate endDate = LocalDate.now();
        List<AddressBookData> addressBookDataList = addressBook.readContactDataForGivenDateRange(startDate, endDate);
        Assert.assertEquals(3, addressBookDataList.size());
    }

    //UC-19
    @Test
    public void givenContacts_RetrieveNumberOfContacts_ByCityOrState() {
        AddressBook addressBook = new AddressBook();
        addressBook.readAddressBookDataDB(DB_IO);
        Map<String, Integer> contactByCityOrStateMap = addressBook.readContactByCityOrState();
        Assert.assertEquals((int) contactByCityOrStateMap.get("Pune"), 1);
        Assert.assertEquals((int) contactByCityOrStateMap.get("Madhya Pradesh"), 2);
    }

    //UC-20
    @Test
    public void givenNewContact_WhenAdded_ShouldSyncWithDB() {
        AddressBook addressBook = new AddressBook();
        addressBook.readAddressBookDataDB(DB_IO);
        addressBook.addContactToDB(1,"Shreya Patil","Shreya", "Patil", "Hadapsar", "Pune", "Maharashtra", 411056,
                "9988771122", "shreyapatil@gmail.com", Date.valueOf("2021-05-15"));
        boolean result = addressBook.checkAddressBookInSyncWithDB("Shreya Patil");
        Assert.assertTrue(result);
    }

    //UC-21
    @Test
    public void givenContacts_WhenAddedToDB_ShouldMatchEmployeeEntries() {
        AddressBookData[] addressBookArray = {
                new AddressBookData(2,"Pooja Shah","Pooja", "Shah", "Mani Nagar",
                        "Ahmedabad", "Gujarat", 320008, "9856231473",
                        "poojashah@gmail.com", Date.valueOf("2021-05-16")),
                new AddressBookData(3,"Mayuresh Tilkari","Mayuresh", "Tilkari",
                        "Baner", "Pune", "Maharashtra", 411046, "7894231547",
                        "mayureshtilkari@gmail.com", Date.valueOf("2019-04-21"))};
        AddressBook addressBook = new AddressBook();
        addressBook.readAddressBookDataDB(DB_IO);
        Instant start = Instant.now();
        addressBook.addDetails(Arrays.asList(addressBookArray));
        Instant end = Instant.now();
        System.out.println("Duration without thread : " + Duration.between(start, end));
        Instant threadStart = Instant.now();
        addressBook.addDetailsWithThreads(Arrays.asList(addressBookArray));
        Instant threadEnd = Instant.now();
        System.out.println("Duration with Thread : " + Duration.between(threadStart, threadEnd));
        Assert.assertEquals(10, addressBook.countEntries(DB_IO));
    }
}
