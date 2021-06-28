package com.example.lsy.path;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
    public static void main(String[] args) {
        String fileName = "D:\\data\\test\\newFile3.txt";

        try {
            //如果是JDK11用上面的方法，如果不是用这个方法也很容易
            byte[] bytes = Files.readAllBytes(Paths.get(fileName));
            String content = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //java 8中这样写也可以  管道流方式
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))){
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(
                    new String(Files.readAllBytes(Paths.get("C:\\Users\\w\\Desktop\\springcloud\\java8\\src\\main\\java\\com\\example\\java8\\path\\File3.java")))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        //如果是文本文件也可以这么读  调用readAllLines 方法
        try {                                //JDK1.8以后可以省略第二个参数，默认是UTF-8编码
            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\w\\Desktop\\springcloud\\java8\\src\\main\\java\\com\\example\\java8\\path\\File3.java"), StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line + "\n");//  \r\n  换行符
            }
            String fromFile = sb.toString();
            System.out.println(fromFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getXml() {
        StringBuffer sb = new StringBuffer();
        Path path = Paths.get("C:\\Users\\Administrator\\Desktop\\xml.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(str -> sb.append(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
