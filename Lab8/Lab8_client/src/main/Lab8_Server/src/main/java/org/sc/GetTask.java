package org.sc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.RecursiveTask;

public class GetTask extends RecursiveTask<Request> {
    private DatagramSocket socket;
    private byte[] buffer = new byte[5000];
    @Override
    protected Request compute() {
        Request request;
        try {
            socket = new DatagramSocket(33011);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet); // Получение пакета от клиента
            socket.close();

            Server.setAddress(packet.getAddress());
            Server.setPort(packet.getPort());

            // Извлечение данных из пакета
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            request = (Request) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return request;
    }
}
