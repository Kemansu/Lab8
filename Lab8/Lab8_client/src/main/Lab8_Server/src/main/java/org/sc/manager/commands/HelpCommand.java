package org.sc.manager.commands;

import org.sc.Request;
import org.sc.manager.CommandManager;

import java.util.LinkedHashMap;
/**
 *  Данная команда выводит информацию про команды, которые можно использовать в консольномо приложении
 *
 * @author Kemansu
 * @since 1.0
 */
public class HelpCommand implements Command{
    @Override
    public String execute(Request request) throws Exception {
        StringBuilder line = new StringBuilder();
        LinkedHashMap<String, Command> commandList = CommandManager.getCommandList();
        for (String name: commandList.keySet()){
            Command command = commandList.get(name);
            if (command.getName().isEmpty()){
                continue;
            }
            line.append(command.getName()).append(" - ").append(command.getDescription()).append("\n");
        }

        line.append("execute_script_command").append(" - ").append("считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");

        return line.toString();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
