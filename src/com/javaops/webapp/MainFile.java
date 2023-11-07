package com.javaops.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        allFiles("./");
    }

    public static void allFiles(String path) {
        File file = new File(path);
        for (File curFile : Objects.requireNonNull(file.listFiles())) {
            if (curFile.isDirectory()) {
                allFiles(curFile.getPath());
            } else {
                System.out.println(curFile.getName());
            }
        }
    }
}
