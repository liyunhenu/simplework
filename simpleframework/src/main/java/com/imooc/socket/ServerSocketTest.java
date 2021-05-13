package com.imooc.socket;

import sun.security.util.Length;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(8080);
            while (true){
                Socket socket=serverSocket.accept();
                new LengthCaculator(socket).start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
