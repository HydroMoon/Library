package com.hydro.library;

public class Lectures {

    private String lecName;
    private String lecIcon;

    Lectures(String lName, String lIcon) {
        lecName = lName;
        lecIcon = lIcon;
    }

    public String getLecName() {
        return lecName;
    }

    public String getLecIcon() {
        return lecIcon;
    }
}
