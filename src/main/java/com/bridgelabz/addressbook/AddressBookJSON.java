package com.bridgelabz.addressbook;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AddressBookJSON {

    private static final String fileName = "address-book.json";

    public static boolean writeData(List<AddressBookData> addressBookData) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            gson.toJson(addressBookData, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void readData(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AddressBookData addressBookData = objectMapper.readValue(new FileReader(filePath), AddressBookData.class);
            System.out.println(objectMapper.writeValueAsString(addressBookData));
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
