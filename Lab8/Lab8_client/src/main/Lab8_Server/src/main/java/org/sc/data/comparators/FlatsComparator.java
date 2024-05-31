package org.sc.data.comparators;

import org.sc.data.Flat;

import java.util.Comparator;
/**
 * Данный comparator объекты типа Flat дома между собой
 *
 * @author Kemansu
 * @since 1.0
 */
public class FlatsComparator implements Comparator<Flat> {
    @Override
    public int compare(Flat flat1, Flat flat2) {
        if (flat1.getArea().compareTo(flat2.getArea()) == 0) {
            return flat1.getNumberOfRooms().compareTo(flat2.getNumberOfRooms());
        }
        return flat1.getArea().compareTo(flat2.getArea());
    }
}
