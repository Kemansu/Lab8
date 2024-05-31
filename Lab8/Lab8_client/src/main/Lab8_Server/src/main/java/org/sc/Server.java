package org.sc;


import java.net.InetAddress;
import java.util.concurrent.ForkJoinPool;

public class Server {
    private static InetAddress address;
    static int port;
    private final ForkJoinPool pool = new ForkJoinPool(5);



    public void listen() throws Exception {
        while (true) {
            // многопоточное приянтие данных
            GetTask getTask = new GetTask();
            Request request = pool.invoke(getTask);

            // многопоточная обработка полученного запроса
            ProcessTask processTask = new ProcessTask(request);
            String message = pool.invoke(processTask);
            request.setMessage(message);


            // многопоточная отправка ответа
            SendTask sendTask = new SendTask(address, port, request);
            pool.execute(sendTask);
        }

    }


    public static void setAddress(InetAddress address1) {
        address = address1;
    }

    public static void setPort(int port1) {
        port = port1;
    }
}