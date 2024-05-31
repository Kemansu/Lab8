package org.sc.manager.commands;

import org.sc.Request;
import org.sc.data.Flat;
import org.sc.data.comparators.FlatsComparator;
import org.sc.manager.CollectionManager;
import org.sc.manager.DBmanager;

import java.util.ArrayDeque;
import java.util.List;

/**
 *  Данная команда удаляет из коллекции все элементы, меньшие, чем заданный
 *
 *
 * @author Kemansu
 * @since 1.0
 */

public class RemoveLowerCommand implements Command{
    @Override
    public String execute(Request request) throws Exception {
        // Заданный параметр
        Flat flat_param = request.getFlat();
        long user_id = request.getId();

        FlatsComparator c = new FlatsComparator();
        int counter;

        List<Flat> list = CollectionManager.getArrayDeque().stream().filter(x -> c.compare(x, flat_param) >= 0 || x.getOwnerId() != user_id).toList();
        counter = (CollectionManager.getArrayDeque().size() - list.size());

        ArrayDeque<Flat> arrayDeque = new ArrayDeque<>(list);
        CollectionManager.setArrayDeque(arrayDeque);
        DBmanager.Synchronized();

        return String.valueOf(counter);
    }

    @Override
    public String getName() {
        return "remove_lower";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
