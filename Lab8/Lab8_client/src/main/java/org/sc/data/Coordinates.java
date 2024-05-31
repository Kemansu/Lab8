package org.sc.data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Модель объекта "координаты"
 * содержит геттеры/сеттеры
 * @author Kemansu
 * @since 1.0
 */
public class Coordinates implements Serializable {
    @Serial
    private static final long serialVersionUID = 5760575944040770153L;
    private Double x; //Значение поля должно быть больше -772, Поле не может быть null
    private long y; //Максимальное значение поля: 793

    public Coordinates(double coordinateX, long coordinateY) {
        x = coordinateX;
        y = coordinateY;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(long y) {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public long getY() {
        return y;
    }
}
