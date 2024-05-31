package org.sc;

import org.sc.data.Flat;
import org.sc.data.House;
import org.sc.data.generators.FlatGenerator;
import org.sc.data.generators.HouseGenerator;
import org.sc.manager.ExecuteScriptCommand;

import java.io.InputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Console {
    private Request request;
    public void start(InputStream inputStream) throws Exception {
        Scanner scanner = new Scanner(inputStream);
        Server client = new Server();
        String input;
        boolean status_of_ath = false;
        System.out.println("Welcome to application!");
        System.out.println(
                "if you a new user, then type 'create' to create a new account\n" +
                "if you already have an account - type 'log_in' to authorized"
        );

        while (!status_of_ath) {
            input = scanner.nextLine();

            if (input.equals("create")) {
                System.out.println("create username");
                String username = scanner.nextLine();
                System.out.println("create password");
                String password = scanner.nextLine();
                request = new Request("create", null, password, username);

                if (client.sendEcho(request).equals("User created successfully")){
                    status_of_ath = true;
                    System.out.println("Great! Now you have log in!");
                } else {
                    System.out.println("user with that data is already exist");
                }

            } else if (input.equals("log_in")) {
                System.out.println("input username");
                String username = scanner.nextLine();
                System.out.println("input password");
                String password = scanner.nextLine();
                request = new Request("log_in", null, password, username);

                if (client.sendEcho(request).equals("true")){
                    status_of_ath = true;
                    System.out.println("Great! Now you have log in!");
                } else {
                    System.out.println("wrong username or a password");
                }
            } else {
                System.out.println("you can't use this application without authorization");
            }
        }


        while (true) {
            try {
                client = new Server();
                input = scanner.nextLine();
                request.setMessage(input);

                if (input.equals("add") || input.equals("add_if_min") || input.equals("remove_greater") || input.equals("remove_lower")) {
                    Flat flat = FlatGenerator.createFlat(0L);
                    request.setFlat(flat);

                } else if (input.split(" ")[0].equals("update")) {
                    String anwser = client.sendEcho(request);

                    if (!anwser.equals("id is good")) {
                        System.out.println(anwser);
                        continue;
                    } else {
                        System.out.println("Let's get a new flat");
                        request.setFlat(FlatGenerator.createFlat(0L));
                    }

                } else if (input.equals("exit")) {
                    System.exit(1);
                } else if (input.equals("count_greater_than_house")) {
                    House house = HouseGenerator.createHouse();
                    request.setHouse(house);
                }

                if (input.split(" ")[0].equals("execute_script_command")) {
                    ExecuteScriptCommand.execute(request.getMessage());
                } else {
                    String echo = client.sendEcho(request);
                    System.out.println("Echo from server: \n" + echo);
                    client.close();
                }


            } catch (SocketException e) {
                System.out.println("SocketException: \n" + e.getMessage());
            } catch (UnknownHostException e) {
                System.out.println("UnknownHostException: \n" + e.getMessage());
            }
        }

    }

}
