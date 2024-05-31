package org.sc.manager.commands;

import org.sc.Request;
import org.sc.data.Flat;
import org.sc.manager.CollectionManager;

import java.util.ArrayDeque;
/**
 *  Данная команда выводит все элементы коллекции
 *
 * @author Kemansu
 * @since 1.0
 */
public class ShowCommand implements Command{
    @Override
    public String execute(Request request) throws Exception {
        ArrayDeque<Flat> flats = CollectionManager.getArrayDeque();
        StringBuilder stringBuilder = new StringBuilder();
        if (flats.isEmpty()){
            return "No flats :(";
        } else{
            for (Flat flat : flats) {
                stringBuilder.append(flat + "/");
            }
            return stringBuilder.toString();
        }
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
