package com.example.lsy.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class File2 {
    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get("C:\\Users\\w\\desktop\\mystuff.txt"));
            if (!Files.exists(Paths.get("C:\\Users\\w\\desktop\\mystuff.txt")))
                Files.createFile(Paths.get("C:\\Users\\w\\desktop\\mystuff.txt/"));
//            Files.createDirectories(Paths.get("C:\\Users\\w\\desktop\\mystuff.txt/test2.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.createDirectories(Paths.get("C://TEST"));
            if (!Files.exists(Paths.get("C://TEST")))
                Files.createFile(Paths.get("C://TEST/test.txt"));
//          Files.createDirectories(Paths.get("C://TEST/test2.txt"));
            Files.copy(Paths.get("C://my.ini"), System.out);
            Files.copy(Paths.get("C://my.ini"), Paths.get("C://my2.ini"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(System.in, Paths.get("C://my3.ini"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
