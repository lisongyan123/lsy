package com.example.lsy.path;

import java.io.*;

public class File7 {
    public static void main(String args[])throws IOException {

        DataInputStream in = new DataInputStream(new FileInputStream("C:\\Users\\w\\Desktop\\test.txt"));
        DataOutputStream out = new DataOutputStream(new FileOutputStream("test1.txt"));
        BufferedReader d  = new BufferedReader(new InputStreamReader(in));

        String count;
        while((count = d.readLine()) != null){
            String u = count.toUpperCase();
            System.out.println(u);
            out.writeBytes(u + "  ,");
        }
        d.close();
        out.close();
    }
}
