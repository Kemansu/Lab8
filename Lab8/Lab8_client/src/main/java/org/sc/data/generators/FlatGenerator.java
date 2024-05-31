package org.sc.data.generators;

import org.sc.data.*;
import org.sc.exceptions.WrongInputException;

import java.util.Scanner;

/**
 * Данный класс отвечает за генерацию объектов типа Flat
 *
 * @author Kemansu
 * @since 1.0
 */

public class FlatGenerator {
    public static Flat createFlat(Long id) throws WrongInputException {

        Flat flat = new Flat(id);
        String input;
        double x1;
        long y1;
        Coordinates coordinates;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Input name (String):");
            try {
                input = String.valueOf(scanner.nextLine());
                if (input.isEmpty() | input.equals(null)) {
                    throw new WrongInputException();
                }
                flat.setName(input);
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + scanner.nextLine());
            }
        }

        while (true) {
            System.out.println("Input x coordinate (Double):");
            try {
                input = scanner.nextLine();
                x1 = Double.parseDouble(input);
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + input);
            }
        }

        while (true) {
            System.out.println("Input y coordinate (long):");
            try {
                input = scanner.nextLine();
                y1 = Long.parseLong(input);
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + input);
            }
        }

        coordinates = new Coordinates(0, 0);

        coordinates.setX(x1);
        coordinates.setY(y1);

        flat.setCoordinates(coordinates);


        while (true) {
            System.out.println("Input area (long):");
            try {
                input = scanner.nextLine();
                flat.setArea(Long.parseLong(input));
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + input);
            }
        }


        while (true) {
            System.out.println("Input number Of Rooms (long):");
            try {
                input = scanner.nextLine();
                if (Long.parseLong(input) <= 0) {
                    throw new WrongInputException();
                }
                flat.setNumberOfRooms(Long.parseLong(input));
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + input);
            }
        }


        while (true) {
            System.out.println("Input kitchen Area (Double):");
            try {
                input = scanner.nextLine();
                if (Long.parseLong(input) <= 0) {
                    throw new WrongInputException();
                }
                flat.setKitchenArea(Double.valueOf(input));
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + input);
            }
        }

        while (true) {
            System.out.println("Input view (View):");
            try {
                input = scanner.nextLine();
                flat.setView(View.valueOf(input));
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + input);
            }
        }


        while (true) {
            System.out.println("Input transport (Transport):");
            try {
                input = scanner.nextLine();
                flat.setTransport(Transport.valueOf(input));
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + input);
            }
        }

        System.out.println("Now, let's choose a House!\n");

        House house = new House();

        while (true) {
            System.out.println("Input name of House (String):");
            try {
                input = scanner.nextLine();
                house.setName(input);
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + input);
            }
        }


        while (true) {
            System.out.println("Input year of House (Long):");
            try {
                input = scanner.nextLine();
                house.setYear(Long.valueOf(input));
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + input);
            }
        }

        while (true) {
            System.out.println("Input number Of Flats On Floor (Integer):");
            try {
                input = scanner.nextLine();
                house.setNumberOfFlatsOnFloor(Integer.valueOf(input));
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + input);
            }
        }

        flat.setHouse(house);


        return flat;
    }
    public static Flat createFlat(Long id, String name, Double coordinateX, Long coordinateY, Long area, Long numberOfRooms, Double kitchenArea, View view, Transport transport, House house) throws WrongInputException {
        Flat flat = new Flat(id);

        flat.setName(name);
        flat.getCoordinates().setX(coordinateX);
        flat.getCoordinates().setY(coordinateY);
        flat.setArea(area);
        flat.setNumberOfRooms(numberOfRooms);
        flat.setKitchenArea(kitchenArea);
        flat.setView(view);
        flat.setTransport(transport);
        flat.setHouse(house);

        return flat;
    }
}
