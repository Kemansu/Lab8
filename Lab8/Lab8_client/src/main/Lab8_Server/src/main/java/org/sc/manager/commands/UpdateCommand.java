package org.sc.manager.commands;

import org.sc.Request;
import org.sc.data.Flat;
import org.sc.manager.CollectionManager;
import org.sc.manager.DBmanager;

import java.util.ArrayDeque;
import java.util.List;

/**
 *  Данная команда обновляет элемент коллекции по заданному id
 *
 * @author Kemansu
 * @since 1.0
 */

public class UpdateCommand implements Command {
    @Override
    public String execute(Request request) throws Exception {
        if (!CollectionManager.isIdExist(Long.parseLong(request.getMessage().split(" ")[1]))) {
            return  "no flats with that id";
        } else if (CollectionManager.getFlatById(Long.parseLong(request.getMessage().split(" ")[1])).getOwnerId() != request.getId()) {
            return "it's not you'r flat, so you can't update it";
        } else {
            if (request.getFlat() == null){
                return "id is good";
            }
            Long neededId = Long.parseLong(request.getMessage().split(" ")[1]);
            DBmanager.removeFlat(Math.toIntExact(neededId));
            List<Flat> list = CollectionManager.getArrayDeque().stream().filter(x -> !x.getId().equals(neededId)).toList();
            ArrayDeque<Flat> updatedArraydeque = new ArrayDeque<>(list);
            Flat newflat = request.getFlat();
            newflat.setId(neededId);
            newflat.setOwnerId(request.getId());
            DBmanager.addFlat(newflat, request.getFlat().getId());
            updatedArraydeque.add(newflat);
            CollectionManager.setArrayDeque(updatedArraydeque);

            return "Flat successfully updated";
        }
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}