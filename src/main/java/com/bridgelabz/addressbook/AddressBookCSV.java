package com.bridgelabz.addressbook;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class AddressBookCSV {

    public static String ADDRESS_BOOK_CSV_FILE = "address-book.csv";
    public static String[] HEADER = {"firstname", "lastname", "address", "city", "state", "zip", "phoneNumber", "email"};

    public void writeCSVData() {
        try {
            FileWriter fileWriter = new FileWriter(ADDRESS_BOOK_CSV_FILE);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeNext(HEADER);

            List<String[]> list = new ArrayList<>();
            list.add(new String[]{"Virat", "Kohli", "Saket vihar", "Delhi", "Delhi", "400112", "7788554466", "viratkohli@gmail.com"});
            list.add(new String[]{"Rohit", "Sharma", "Bandra", "Mumbai", "MH", "411112", "7788112233", "rohitsharma@gmail.com"});
            list.add(new String[]{"Ravindra", "Jadeja", "Kalvad Road", "Jamnagar", "Gujarat", "423568", "9988776655", "ravindrajadeja@gmail.com"});

            csvWriter.writeAll(list);
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int readData (String filePathCSV) throws ContactException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePathCSV))) {
            CsvToBean<ContactCSV> csvToBean = new CsvToBeanBuilder<ContactCSV>(reader)
                    .withType(ContactCSV.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<ContactCSV> addressBookCSVIterator = csvToBean.iterator();
            Iterable<ContactCSV> contactCSVIterable = () -> addressBookCSVIterator;
            return (int) StreamSupport.stream(contactCSVIterable.spliterator(), false).count();
        } catch (IOException exception) {
            throw new ContactException(exception.getMessage(), ContactException.ExceptionType.CSV_FILE_EXCEPTION);
        }
    }
}