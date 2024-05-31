package org.sc.manager.commands;

import org.sc.Request;
import org.sc.data.Flat;
import org.sc.data.comparators.*;
import org.sc.manager.CollectionManager;
import org.sc.manager.DBmanager;

import java.util.List;

/**
 * Данная команда добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
 *
 * @author Kemansu
 * @since 1.0
 */
public class AddIfMinCommand implements Command{
    @Override
    public String execute(Request request) throws Exception {
        // Преобразуем коллекцию в лист
        List<Flat> arrayDeque_list = CollectionManager.getArrayDeque().stream().toList();

        // Проверка на минимум (через сортировку)
        FlatsComparator c = new FlatsComparator();
        if (CollectionManager.getArrayDeque().size() == 0) {
            return "Flat did not suit :(";
        }

        Flat min_flat = arrayDeque_list.stream().min(c).get();

        Flat flat = request.getFlat();

        if (c.compare(flat, min_flat) < 0) {
            flat.setOwnerId(request.getId());
            CollectionManager.add(flat);
            DBmanager.addFlat(flat, 0);
            return "Flat added";
        } else {
            return "Flat did not suit :(";
        }

    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}
