package org.sc.data.comparators;

import org.sc.data.House;

import java.util.Comparator;
/**
 * Данный comparator сравнивает дома между собой
 *
 * @author Kemansu
 * @since 1.0
 */
public class HousesComparator implements Comparator<House> {

    @Override
    public int compare(House house1, House house2) {
        return house1.getNumberOfFlatsOnFloor().compareTo(house2.getNumberOfFlatsOnFloor());
    }
}
