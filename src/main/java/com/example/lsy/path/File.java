package com.example.lsy.path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Iterator;
import java.util.stream.Stream;

public class File {
    public static void main(String[] args) throws IOException {
        //创建文件
        Path target2 = Paths.get("C:\\Users\\w\\Desktop\\mystuff.txt");
        try {
            if (!Files.exists(target2))
                Files.createFile(target2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // C:\Users\w\desktop\mystuff.txt
        Path path = FileSystems.getDefault().getPath("C:/", "Users", "w", "desktop", "mystuff.txt");
        System.out.println(path.toString());

        //文件写操作
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("C:\\Users\\w\\Desktop\\mystuff.txt"), StandardCharsets.UTF_8);
            writer.write("测试文件写操作");
            writer.flush();
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //文件读操作
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\Users\\w\\Desktop\\mystuff.txt"), StandardCharsets.UTF_8);
            String str = null;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //遍历一个文件夹
        Path dir = Paths.get("C:\\Users\\w\\Desktop\\springcloud");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path e : stream) {
                System.out.println(e.getFileName());
            }
        } catch (IOException e) {
        }
        //.git
        //.gitignore
        //.idea
        //.mvn
        //admin-client
        //admin-server
        //airm-ltgj-analyse-service
        //airm-ltgj-analyse-service.zip
        //alibaba
        //cloud-service
        //config-server
        //elastic-search
        //gateway
        //HELP.md
        //java8
        //mvnw
        //mvnw.cmd
        //pom.xml
        //settings.xml
        //springcloud.iml
        //stream-kafka
        //stream-kafka1
        //stream-kafka2
        //webflux
        //zipkin
        //zuul
        System.out.println("*********************");

        try (Stream<Path> stream = Files.list(Paths.get("C:/"))) {
            Iterator<Path> ite = stream.iterator();
            while (ite.hasNext()) {
                Path pp = ite.next();
                System.out.println(pp.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //$Recycle.Bin
        //Boot
        //bootmgr
        //BOOTNXT
        //bootTel.dat
        //Documents and Settings
        //hiberfil.sys
        //kingsoft
        //pagefile.sys
        //PerfLogs
        //Program Files
        //Program Files (x86)
        //ProgramData
        //Recovery
        //swapfile.sys
        //System Volume Information
        //Users
        //Windows
    }
}
