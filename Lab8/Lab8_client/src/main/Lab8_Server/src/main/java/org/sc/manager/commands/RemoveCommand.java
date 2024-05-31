package org.sc.manager.commands;

import org.sc.Request;
import org.sc.data.Flat;
import org.sc.manager.CollectionManager;
import org.sc.manager.DBmanager;

import java.util.ArrayDeque;

/**
 *  Данная команда удаляет из коллекции элемент, по заданному id
 *
 * @author Kemansu
 * @since 1.0
 */
public class RemoveCommand implements Command{
    @Override
    public String execute(Request request) throws Exception {
        if (!CollectionManager.isIdExist(Long.parseLong(request.getMessage().split(" ")[1]))) {
            return  "no flats with that id";
        } else if (CollectionManager.getFlatById(Long.parseLong(request.getMessage().split(" ")[1])).getOwnerId() != request.getId()) {
            return "it's not you'r flat, so you can't delete it";
        } else {
            Long neededId = Long.parseLong(request.getMessage().split(" ")[1]);
            Object[] array = CollectionManager.getArrayDeque().toArray();

            // заносим все в обновленный массив
            ArrayDeque<Flat> updatedArraydeque = new ArrayDeque<>();

            for (Object object : array) {
                Flat flat = (Flat) object;
                if (flat.getId() != neededId) {
                    updatedArraydeque.add(flat);
                }
            }

            DBmanager.removeFlat(Math.toIntExact(neededId));
            CollectionManager.setArrayDeque(updatedArraydeque);
            return "flat successfully removed";
        }
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }
}
