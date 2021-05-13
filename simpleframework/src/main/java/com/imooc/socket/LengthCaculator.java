package com.imooc.socket;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class LengthCaculator extends Thread {
    Socket socket;

    public LengthCaculator(Socket serverSocket) {
        this.socket = serverSocket;
    }

    @SneakyThrows
    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            int ch;
            byte[] buff = new byte[1024];
            ch = inputStream.read(buff);
            String context = new String(buff, 0, ch);
            System.out.println(context);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(String.valueOf(context.length()).getBytes());
            outputStream.close();
            inputStream.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(inputStreamReader.read());

    }
}
