package org.sc;

import org.sc.manager.CollectionManager;
import org.sc.manager.CommandManager;
import org.sc.manager.DBmanager;

import java.net.SocketException;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandManager commandManager = new CommandManager();
        CollectionManager collectionManager = new CollectionManager();
        DBmanager.ReadDB();

        try {
            Server server = new Server();
            server.listen();
        } catch (SocketException e) {
            System.out.println("SocketException: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
