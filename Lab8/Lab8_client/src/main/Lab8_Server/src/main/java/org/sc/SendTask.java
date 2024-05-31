package org.sc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.RecursiveTask;

public class SendTask extends RecursiveTask<Void> {
    private InetAddress address;
    private int port;
    private Request request;

    public SendTask(InetAddress address, int port, Request request){
        this.address = address;
        this.port = port;
        this.request = request;
    }

    @Override
    protected Void compute() {
        try {
            DatagramSocket socket = new DatagramSocket();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            objectOutputStream.close();
            DatagramPacket sendPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.toByteArray().length, address, port);

            socket.send(sendPacket);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
