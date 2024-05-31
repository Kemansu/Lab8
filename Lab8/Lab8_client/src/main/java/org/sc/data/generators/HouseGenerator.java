package org.sc.data.generators;

import org.sc.data.House;
import org.sc.exceptions.WrongInputException;

import java.util.Scanner;

/**
 * Данный класс отвечает за генерацию объектов типа House
 *
 * @author Kemansu
 * @since 1.0
 */
public class HouseGenerator {
    public static House createHouse(){
        String input = null;
        Scanner scanner = new Scanner(System.in);

        House house = new House();

        while (true) {
            System.out.println("Input name of House (String):");
            try {
                input = scanner.nextLine();
                if (input.isEmpty() | input.equals(null)) {
                    throw new WrongInputException();
                }
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
                if (Long.parseLong(input) <= 0) {
                    throw new WrongInputException();
                }
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
                if (Integer.valueOf(input) <= 0) {
                    throw new WrongInputException();
                }
                house.setNumberOfFlatsOnFloor(Integer.valueOf(input));
                break;
            } catch (Exception e){
                System.out.println("Wrong input: " + input);
            }
        }


        return house;
    }
}
