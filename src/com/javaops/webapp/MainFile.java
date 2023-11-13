package com.javaops.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        allFiles("./", "-");
    }

    public static void allFiles(String path, String sep) {
        File file = new File(path);
        for (File curFile : Objects.requireNonNull(file.listFiles())) {
            if (curFile.isDirectory()) {
                System.out.println(sep + curFile.getName());
                allFiles(curFile.getPath(), sep + "-");
            } else {
                System.out.println(sep + curFile.getName());
            }
        }
    }
}
