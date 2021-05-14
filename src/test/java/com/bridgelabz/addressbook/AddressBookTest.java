package com.bridgelabz.addressbook;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
        addressBook.updateAddressBook("Mohit Shah", "1234567890");
        boolean result = addressBook.checkAddressBookInSyncWithDB("Mohit Shah");
        Assert.assertTrue(result);
    }

}
