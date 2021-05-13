package com.imooc.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UdpClient {

    public static void main(String[] args) {

        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            byte[] sendontext = "UDP 报文".getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(sendontext, sendontext.length, InetAddress.getByName("127.0.0.1"), 8081);
            datagramSocket.send(datagramPacket);

            byte[] buff = new byte[100];
            DatagramPacket receivePacket=new DatagramPacket(buff,0,buff.length);
            datagramSocket.receive(receivePacket);
            byte[] receiveData=receivePacket.getData();
            System.out.println(new String(receiveData,0,receivePacket.getLength()));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
