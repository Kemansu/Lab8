package org.sc.exceptions;
/**
 * Данное исключение работает с неизвестной введенной командой
 *
 * @author Kemansu
 * @since 1.0
 */
public class UnknownCommandException extends Exception{
    public UnknownCommandException(String command){
        super("Unknown command: " + command);
    }
}
