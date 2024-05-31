package org.sc.manager.commands;

import org.sc.Request;
import org.sc.data.Flat;
import org.sc.data.comparators.FlatTransportsComparator;
import org.sc.manager.CollectionManager;

import java.util.List;

/**
 *  Данная команда выводит значения поля transport всех элементвов в порядке возрастания
 *
 * @author Kemansu
 * @since 1.0
 */
public class PrintSortedTransport implements Command{
    @Override
    public String execute(Request request) throws Exception {
        // Начало
        List<Flat> arrayDeque_list = CollectionManager.getArrayDeque().stream().sorted(new FlatTransportsComparator()).toList();
        StringBuilder stringBuilder = new StringBuilder();


        for (Flat flat : arrayDeque_list) {
            stringBuilder.append(flat.getTransport() + "\n");
        }
        if (CollectionManager.getArrayDeque().size() == 0) {
            return "no flats in collection :(";
        }
        return stringBuilder.toString();
    }

    @Override
    public String getName() {
        return "print_field_ascending_transport";
    }

    @Override
    public String getDescription() {
        return "вывести значения поля transport всех элементвов в порядке возрастания";
    }
}
