package org.sc.manager;


import org.sc.Request;
import org.sc.manager.commands.*;

import java.util.LinkedHashMap;

/**
 *  Данный класс обеспечивает связь между командами и CollectionManager
 *
 * @author Kemansu
 * @since 1.0
 */

public class CommandManager {
    private static LinkedHashMap<String, Command> commandList;

    public CommandManager(){
        commandList = new LinkedHashMap<>();
        commandList.put("help", new HelpCommand());
        commandList.put("add", new AddCommand());
        commandList.put("info", new InfoCommand());
        commandList.put("show", new ShowCommand());
        commandList.put("update", new UpdateCommand());
        commandList.put("remove_by_id", new RemoveCommand());
        commandList.put("add_if_min", new AddIfMinCommand());
        commandList.put("remove_greater", new RemoveGreaterCommand());
        commandList.put("remove_lower", new RemoveLowerCommand());
        commandList.put("group_counting_by_kitchen_area", new GroupCountingByKitchenArea());
        commandList.put("count_greater_than_house", new GreaterThanHouse());
        commandList.put("print_field_ascending_transport", new PrintSortedTransport());
        commandList.put("create", new CreateUsers());
        commandList.put("log_in", new LogIn());
    }

    public static String startExecuting(Request request) throws Exception {
        String commandName = request.getMessage().split(" ")[0];
        if (!commandList.containsKey(commandName)) {
            return "Unknown command";
        }
        Command command = commandList.get(commandName);
        return command.execute(request);
    }

    public static LinkedHashMap<String, Command> getCommandList() {
        return commandList;
    }


}
