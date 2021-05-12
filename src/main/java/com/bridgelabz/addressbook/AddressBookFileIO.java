package com.bridgelabz.addressbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddressBookFileIO {
    public static String ADDRESS_BOOK_FILE = "address-book-file.txt";

    public void writeData(List<AddressBookData> employeePayrollList) {
        StringBuffer empBuffer = new StringBuffer();
        employeePayrollList.forEach(employee -> {
            String employeeDataString = employee.toString().concat("\n");
            empBuffer.append(employeeDataString);
        });

        try{
            Files.write(Paths.get(ADDRESS_BOOK_FILE), empBuffer.toString().getBytes());
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public long countEntries() {
        long entries = 0;
        try {
            entries = Files.lines(new File(ADDRESS_BOOK_FILE).toPath()).count();
        } catch(IOException e){
            e.printStackTrace();
        }
        return entries;
    }

    public List<AddressBookData> readData() {
        List<AddressBookData> employeePayrollList = new ArrayList<>();
        try{
            Files.lines(new File(ADDRESS_BOOK_FILE).toPath())
                    .map(line -> line.trim())
                    .forEach(line -> System.out.println(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    public void printData(){
        try{
            Files.lines(new File(ADDRESS_BOOK_FILE).toPath())
                    .forEach(System.out::println);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
