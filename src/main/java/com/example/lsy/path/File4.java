package com.example.lsy.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File4 {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:\\Users\\w\\desktop\\mystuff.txt");
        System.out.println(System.getProperty("os.name"));
        System.out.println("toString：" + path);                             //Windows 10
        System.out.println("Exists: " + Files.exists(path));             //toString：C:\Users\w\desktop\mystuff.txt
        System.out.println("RegularFile: " + Files.isRegularFile(path)); //Exists: true
        System.out.println("Readable: " + Files.isReadable(path));          //RegularFile: true
        System.out.println("Hidden: " + Files.isHidden(path));           //Readable: true
        System.out.println("Directory: " + Files.isDirectory(path));            //Hidden: false
        System.out.println("Absolute: " + path.isAbsolute());            //Directory: false
        System.out.println("Parent Path: " + path.getParent());          //Absolute: true
        System.out.println("FileName: " + path.getFileName());           //Parent Path: C:\Users\w\desktop
        System.out.println("Root: " + path.getRoot());                   //FileName: mystuff.txt
//        Root: C:\
    }
}
