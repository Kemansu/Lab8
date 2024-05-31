package org.sc.manager.commands;

import org.sc.Request;
import org.sc.manager.CollectionManager;
/**
 *  Данная команда выводит различную информацию про коллекцию и ее содержимое
 *
 * @author Kemansu
 * @since 1.0
 */
public class InfoCommand implements Command{
    @Override
    public String execute(Request request) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type - " + CollectionManager.getArrayDeque().getClass().getName() + "\n");
        stringBuilder.append("Count of Flats - " + CollectionManager.getArrayDeque().size() + "\n");
        stringBuilder.append("Init date - " + CollectionManager.getInitDate() + "\n");
        return stringBuilder.toString();
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
