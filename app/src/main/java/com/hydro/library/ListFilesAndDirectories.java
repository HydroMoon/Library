package com.hydro.library;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListFilesAndDirectories {

    private List<Object> LocalFilesList = new ArrayList<>();
    private String Lecture;

    ListFilesAndDirectories(String mLecture) {
        Lecture = mLecture;
    }



    private String FileSize (double bytes) {
        String size = "";
        if (bytes >= 1048576) {
            size = String.format("Size %.2f", bytes / 1048576) + " MB";
        } else if (bytes >= 1024) {
            size = String.format("Size %.2f", bytes / 1024) + " KB";
        } else if (bytes > 1) {
            size = bytes + " bytes";
        } else if (bytes == 1) {
            size = bytes + " byte";
        } else {
            size = "0 bytes";
        }
        return size;
    }

    public List<Object> walk(File root) {

        File[] list = root.listFiles();
        String sub = "";

        if (list != null) {
            for (File f : list) {
                if (f.isDirectory()) {
                    Log.i("DIR_SEL", "Dir: " + f.getName());
                    LocalFilesList.add(getFullName(f.getName()));
                    sub = f.getName();
                    walk(f);
                }
                else {
                    Log.i("DIR_SEL", "File: " + f.getName() + "File Size: " + FileSize(f.length()));
                    LocalFilesList.add(new BooksInfo(f.getAbsolutePath(), FileSize(f.length()), sub, Lecture));
                }
            }
        }


        return LocalFilesList;
    }

    private String getFullName(String sub) {
        String name = "";
        switch (sub) {
            case "M":
                name = "Lectures";
                break;
            case "L":
                name = "Labs";
                break;
            case "E":
                name = "Exams";
                break;
            case "O":
                name = "Others";
                break;
        }
        return name;
    }
}
