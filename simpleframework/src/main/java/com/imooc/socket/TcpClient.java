package com.imooc.socket;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {

    public static void main(String[] args) {
        try{
            Socket socket=new Socket("127.0.0.1",8080);
            InputStream io=socket.getInputStream();
            OutputStream outputStream=socket.getOutputStream();
            outputStream.write("TCP报文".getBytes());
            int ch;
            byte[] buffer=new byte[1024];
            ch=io.read(buffer);
            System.out.println(new String(buffer,0,ch));
            io.close();
            outputStream.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
