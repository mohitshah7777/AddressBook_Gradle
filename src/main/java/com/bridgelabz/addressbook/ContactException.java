package com.bridgelabz.addressbook;

public class ContactException extends Exception {

    public ExceptionType type;

    public ContactException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public enum ExceptionType {
        CSV_FILE_EXCEPTION
    }
}
