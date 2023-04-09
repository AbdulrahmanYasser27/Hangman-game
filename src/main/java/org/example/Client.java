package org.example;

import java.io.*;
import java.net.ServerSocket;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 6660);
            System.out.println("Connected to server");
            BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line = r.readLine();
            System.out.println(line);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

