package org.sc.manager.commands;


import org.sc.Request;

/**
 *  Базовый интерфейс для реализации команд. Такие команды содержаться в CollectionManager
 *
 * @author Kemansu
 * @since 1.0
 */

public interface Command {
    public String execute(Request request) throws Exception;
    public String getName();

    public String getDescription();
}
