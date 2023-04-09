package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    static Thread thr;
    Socket socket;

    public static Thread getThr() {
        return thr;
    }

    public static void setThr(Thread thr) {
        Server.thr = thr;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Server is running");
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line = "ready";
            w.write(line);
            w.newLine();
            w.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(6660);
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            Server server = new Server();
            server.setSocket(socket);
            thr = new Thread(server);
            thr.start();//call run


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


