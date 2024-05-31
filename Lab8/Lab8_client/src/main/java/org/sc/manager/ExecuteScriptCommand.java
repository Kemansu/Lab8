package org.sc.manager;

import org.sc.Request;
import org.sc.Server;
import org.sc.data.*;
import org.sc.exceptions.WrongInputException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *  Данная команда исполняет скрипт из файла
 *
 * @author Kemansu
 * @since 1.0
 */
public class ExecuteScriptCommand{
    public static void execute(String line) throws Exception {
        Request request = new Request(null);
        String filePath = line.split(" ")[1];

        try {
            Scanner scanner = new Scanner(new FileReader(filePath));
            Server client = new Server();
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                request.setMessage(command);

                if (command.equals("add")) {
                    String name = scanner.nextLine();
                    Double coordinateX = Double.valueOf(scanner.nextLine());
                    Long coordinateY = Long.valueOf(scanner.nextLine());
                    Coordinates coordinates = new Coordinates(coordinateX, coordinateY);
                    Long area = Long.valueOf(scanner.nextLine());
                    Long numberOfRooms = Long.valueOf(scanner.nextLine());
                    Double kitchenArea = Double.valueOf(scanner.nextLine());
                    View view = View.valueOf(scanner.nextLine());
                    Transport transport = Transport.valueOf(scanner.nextLine());

                    House house = new House();

                    String nameHouse = scanner.nextLine();
                    Long yearHouse = Long.valueOf(scanner.nextLine());
                    Integer numberOfFlatsOnFloorHouse = Integer.valueOf(scanner.nextLine());

                    house.setName(nameHouse);
                    house.setYear(yearHouse);
                    house.setNumberOfFlatsOnFloor(numberOfFlatsOnFloorHouse);

                    Flat flat = new Flat(0L);
                    flat.setName(name);
                    flat.setCoordinates(coordinates);
                    flat.setArea(area);
                    flat.setNumberOfRooms(numberOfRooms);
                    flat.setKitchenArea(kitchenArea);
                    flat.setView(view);
                    flat.setTransport(transport);
                    flat.setHouse(house);

                    request.setMessage("add");
                    request.setFlat(flat);
                } else if (command.equals("update")) {

                    Long neededId = Long.valueOf(scanner.nextLine());
                    String name = scanner.nextLine();
                    Double coordinateX = Double.valueOf(scanner.nextLine());
                    Long coordinateY = Long.valueOf(scanner.nextLine());
                    Coordinates coordinates = new Coordinates(coordinateX, coordinateY);
                    Long area = Long.valueOf(scanner.nextLine());
                    Long numberOfRooms = Long.valueOf(scanner.nextLine());
                    Double kitchenArea = Double.valueOf(scanner.nextLine());
                    View view = View.valueOf(scanner.nextLine());
                    Transport transport = Transport.valueOf(scanner.nextLine());

                    House house = new House();

                    String nameHouse = scanner.nextLine();
                    Long yearHouse = Long.valueOf(scanner.nextLine());
                    Integer numberOfFlatsOnFloorHouse = Integer.valueOf(scanner.nextLine());

                    house.setName(nameHouse);
                    house.setYear(yearHouse);
                    house.setNumberOfFlatsOnFloor(numberOfFlatsOnFloorHouse);

                    Flat newflat = new Flat(0L);
                    newflat.setName(name);
                    newflat.setCoordinates(coordinates);
                    newflat.setArea(area);
                    newflat.setNumberOfRooms(numberOfRooms);
                    newflat.setKitchenArea(kitchenArea);
                    newflat.setView(view);
                    newflat.setTransport(transport);
                    newflat.setHouse(house);

                    request.setMessage("update " + neededId);
                    request.setFlat(newflat);

                } else if (command.equals("add_if_min")) {
                    String name = scanner.nextLine();
                    Double coordinateX = Double.valueOf(scanner.nextLine());
                    Long coordinateY = Long.valueOf(scanner.nextLine());
                    Coordinates coordinates = new Coordinates(coordinateX, coordinateY);
                    Long area = Long.valueOf(scanner.nextLine());
                    Long numberOfRooms = Long.valueOf(scanner.nextLine());
                    Double kitchenArea = Double.valueOf(scanner.nextLine());
                    View view = View.valueOf(scanner.nextLine());
                    Transport transport = Transport.valueOf(scanner.nextLine());

                    House house = new House();

                    String nameHouse = scanner.nextLine();
                    Long yearHouse = Long.valueOf(scanner.nextLine());
                    Integer numberOfFlatsOnFloorHouse = Integer.valueOf(scanner.nextLine());

                    house.setName(nameHouse);
                    house.setYear(yearHouse);
                    house.setNumberOfFlatsOnFloor(numberOfFlatsOnFloorHouse);

                    Flat flat = new Flat(0L);
                    flat.setName(name);
                    flat.setCoordinates(coordinates);
                    flat.setArea(area);
                    flat.setNumberOfRooms(numberOfRooms);
                    flat.setKitchenArea(kitchenArea);
                    flat.setView(view);
                    flat.setTransport(transport);
                    flat.setHouse(house);

                    request.setMessage("add_if_min");
                    request.setFlat(flat);
                } else if (command.equals("remove_greater")) {
                    // Заданный параметр
                    // flat_param

                    String name = scanner.nextLine();
                    Double coordinateX = Double.valueOf(scanner.nextLine());
                    Long coordinateY = Long.valueOf(scanner.nextLine());
                    Coordinates coordinates = new Coordinates(coordinateX, coordinateY);
                    Long area = Long.valueOf(scanner.nextLine());
                    Long numberOfRooms = Long.valueOf(scanner.nextLine());
                    Double kitchenArea = Double.valueOf(scanner.nextLine());
                    View view = View.valueOf(scanner.nextLine());
                    Transport transport = Transport.valueOf(scanner.nextLine());

                    House house = new House();

                    String nameHouse = scanner.nextLine();
                    Long yearHouse = Long.valueOf(scanner.nextLine());
                    Integer numberOfFlatsOnFloorHouse = Integer.valueOf(scanner.nextLine());

                    house.setName(nameHouse);
                    house.setYear(yearHouse);
                    house.setNumberOfFlatsOnFloor(numberOfFlatsOnFloorHouse);

                    Flat flat_param = new Flat(0L);
                    flat_param.setName(name);
                    flat_param.setCoordinates(coordinates);
                    flat_param.setArea(area);
                    flat_param.setNumberOfRooms(numberOfRooms);
                    flat_param.setKitchenArea(kitchenArea);
                    flat_param.setView(view);
                    flat_param.setTransport(transport);
                    flat_param.setHouse(house);

                    request.setFlat(flat_param);
                    request.setMessage("remove_greater");

                } else if (command.equals("remove_lower")) {
                    // Заданный параметр
                    String name = scanner.nextLine();
                    Double coordinateX = Double.valueOf(scanner.nextLine());
                    Long coordinateY = Long.valueOf(scanner.nextLine());
                    Coordinates coordinates = new Coordinates(coordinateX, coordinateY);
                    Long area = Long.valueOf(scanner.nextLine());
                    Long numberOfRooms = Long.valueOf(scanner.nextLine());
                    Double kitchenArea = Double.valueOf(scanner.nextLine());
                    View view = View.valueOf(scanner.nextLine());
                    Transport transport = Transport.valueOf(scanner.nextLine());

                    House house = new House();

                    String nameHouse = scanner.nextLine();
                    Long yearHouse = Long.valueOf(scanner.nextLine());
                    Integer numberOfFlatsOnFloorHouse = Integer.valueOf(scanner.nextLine());

                    house.setName(nameHouse);
                    house.setYear(yearHouse);
                    house.setNumberOfFlatsOnFloor(numberOfFlatsOnFloorHouse);

                    Flat flat_param = new Flat(0L);
                    flat_param.setName(name);
                    flat_param.setCoordinates(coordinates);
                    flat_param.setArea(area);
                    flat_param.setNumberOfRooms(numberOfRooms);
                    flat_param.setKitchenArea(kitchenArea);
                    flat_param.setView(view);
                    flat_param.setTransport(transport);
                    flat_param.setHouse(house);

                    request.setFlat(flat_param);
                    request.setMessage("remove_lower");
                } else if (command.equals("exit")) {
                    System.exit(1);
                } else if (command.equals("execute_script_command " + filePath)) {
                    throw new WrongInputException();
                }
                System.out.println(client.sendEcho(request));
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (Exception e) {
            System.out.println("Wrong input data");
        }
    }

    public String getName() {
        return "execute_script_command";
    }

    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме ";
    }
}
