package com.example.lsy.path;

import java.io.*;

public class File6 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\w\\Desktop\\test.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] buf = new byte[100];
        int len = bis.read(buf);
        System.out.println(new String (buf,0,len));
        fis.close();

        FileOutputStream fos = new FileOutputStream("C:\\Users\\w\\Desktop\\test.txt");
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        osw.write("程沐喆");
        //  bw.write("chengmuzhe");
        bw.close();
        osw.close();
    }
}
