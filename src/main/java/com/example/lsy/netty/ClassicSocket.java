package com.example.lsy.netty;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClassicSocket {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8899);

        while (true) new Thread(new Handler(serverSocket.accept())).start();
    }

}

class Handler implements Runnable {

    private final Socket socket;

    Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                System.out.println(line);
                outputStream.write(line.getBytes());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}