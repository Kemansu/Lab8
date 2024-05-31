package org.sc.manager.commands;

import org.sc.Request;
import org.sc.data.Flat;
import org.sc.data.House;
import org.sc.data.comparators.HousesComparator;
import org.sc.manager.CollectionManager;

import java.util.ArrayDeque;
import java.util.Comparator;

/**
 *  Данная команда выводит количество элементов, значение поля house которых больше заданного
 *
 * @author Kemansu
 * @since 1.0
 */
public class GreaterThanHouse implements Command{
    @Override
    public String execute(Request request) throws Exception {
        House house = request.getHouse();
        ArrayDeque<Flat> arrayDeque = CollectionManager.getArrayDeque();
        Comparator<House> comparator = new HousesComparator();


        Integer counter = arrayDeque.stream().filter(x -> comparator.compare(house, x.getHouse()) < 0 ).toList().size();

        return String.valueOf((counter));
    }

    @Override
    public String getName() {
        return "count_greater_than_house";
    }

    @Override
    public String getDescription() {
        return "вывести количество элементов, значение поля house которых больше заданного";
    }
}
