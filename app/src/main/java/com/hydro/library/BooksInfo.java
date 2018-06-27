package com.hydro.library;

public class BooksInfo {

    private String bookName;
    private String bookSize;
    private String bookType;
    private String bookLecture;

    BooksInfo(String bName, String bSize, String bType, String bLecture) {
        bookName = bName;
        bookSize = bSize;
        bookType = bType;
        bookLecture = bLecture;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookSize() {
        return bookSize;
    }

    public String getBookType() {
        return bookType;
    }

    public String getBookLecture() {
        return bookLecture;
    }
}
