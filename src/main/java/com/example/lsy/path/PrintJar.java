package com.example.lsy.path;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
 
/**
 * Print text based resource file inside a jar file. (eg: META-INF/MANIFEST.MF)
 * @author Zemian Deng
 */
public class PrintJar {
    public static void main(String[] args) throws Exception {
        // Search given name in a jar
        JarFile jarFile = new JarFile(args[0]);
        final String searchName = (args.length >= 2) ? args[1] : "application.properties";
        Optional<JarEntry> searchResult = jarFile
                .stream()
                .filter(e -> e.getName().equals(searchName))
                .findFirst();
        if (!searchResult.isPresent())
            throw new RuntimeException(searchName + " not found!");
       
        // Print the JarEntry
        JarEntry entry = searchResult.get();
        try (InputStream instream = jarFile.getInputStream(entry)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
            reader.lines().forEach(line -> System.out.println(line));
        }
    }
}