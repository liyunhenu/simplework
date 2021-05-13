package com.imooc.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpServer {

    public static void main(String[] args) {


        try {
            DatagramSocket datagramSocket = new DatagramSocket(8081);
            byte[] buff = new byte[100];
            DatagramPacket datagramPacket = new DatagramPacket(buff, 0, buff.length);
            datagramSocket.receive(datagramPacket);

            byte[] data = datagramPacket.getData();
            String context = new String(data, 0, datagramPacket.getLength());
            System.out.println(context);
            byte[] sentContext = String.valueOf(context.length()).getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sentContext, sentContext.length, datagramPacket.getAddress(), datagramPacket.getPort());
            datagramSocket.send(sendPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
