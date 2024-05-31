package org.sc.exceptions;
/**
 * Данное исключение работает с не найденным файлом
 *
 * @author Kemansu
 * @since 1.0
 */
public class FileNotFoundException extends Exception{
    public FileNotFoundException(){
        System.out.println("file not found");
    }
}
