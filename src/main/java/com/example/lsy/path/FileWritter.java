package com.example.lsy.path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileWritter {
    public static void main(String[] args) {
        Path fpath = Paths.get("C:\\Users\\w\\Desktop\\create.txt");
        //创建文件
        if (!Files.exists(fpath)) {
            try {
                Files.createFile(fpath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //创建BufferedWriter
        try {
            BufferedWriter bfw = Files.newBufferedWriter(fpath);
            bfw.write("Files类的API:newBufferedWriter");
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建BufferedReader
        try {
            BufferedReader bfr = Files.newBufferedReader(fpath);
            System.out.println(bfr.readLine());
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Java8 新增lines方法
        String filePath = "C:\\Users\\w\\Desktop\\springcloud\\java8\\src\\main\\java\\com\\example\\java8\\path\\File3.java";
        try {
            // Java8用流的方式读文件，更加高效
            Files.lines(Paths.get(filePath)).forEach((line) -> {
                try {
                    Files.write(Paths.get("C:\\Users\\w\\Desktop\\1.java"),
                            line.getBytes(),
                            StandardOpenOption.CREATE_NEW,
                            StandardOpenOption.APPEND);
//					    Files.copy(in, target, options);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

            /* Files.readAllLines(Path path)方法返回值为List<String>类型，就是为Files.write()而设计的
             * 因为Files.write()需要传入一个Iterable<? extends CharSequence>类型的参数
             *
             * Files.write(Path path, Iterable<? extends CharSequence> lines, OpenOption... options)
             */
            List<String> stringStream = Files.readAllLines(Paths.get(filePath));
            //因为Files.lines(Path path)返回的是Stream<String>,所以可以通过下面这种方法变成List<String>
//            List<String> stringStream2= Arrays.asList(Files.lines(Paths.get(filePath)).toArray().toString());

            //StandardOpenOption为枚举类 ,如果当前所Paths.get()的文件不存在，第三个参数可选择StandardOpenOption.CREATE_NEW
            //文件存在则抛java.nio.file.FileAlreadyExistsException异常
            Files.write(Paths.get("C:\\Users\\w\\Desktop\\springcloud\\java8\\src\\main\\java\\com\\example\\java8\\path\\A.java"),
                    stringStream, StandardOpenOption.CREATE_NEW);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}