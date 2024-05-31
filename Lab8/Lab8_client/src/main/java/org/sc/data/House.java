package org.sc.data;

import java.io.Serial;
import java.io.Serializable;

/**
 * модель объекта "дом"
 * содержит геттеры/сеттеры
 *
 * @author Kemansu
 * @since 1.0
 */
public class House implements Serializable {
    @Serial
    private static final long serialVersionUID = 5760575944040770153L;
    private String name; //Поле может быть null
    private Long year; //Значение поля должно быть больше 0
    private Integer numberOfFlatsOnFloor; //Значение поля должно быть больше 0

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public void setNumberOfFlatsOnFloor(Integer numberOfFlatsOnFloor) {
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    public String getName() {
        return name;
    }

    public Long getYear() {
        return year;
    }

    public Integer getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    @Override
    public String toString() {
        return name;
    }
}
