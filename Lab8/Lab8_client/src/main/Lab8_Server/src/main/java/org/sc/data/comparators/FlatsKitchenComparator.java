package org.sc.data.comparators;

import org.sc.data.Flat;

import java.util.Comparator;
/**
 * Данный comparator объекты типа Flat дома между собой по полю Kitchen
 *
 * @author Kemansu
 * @since 1.0
 */
public class FlatsKitchenComparator implements Comparator<Flat> {
    @Override
    public int compare(Flat flat1, Flat flat2) {
        return flat1.getKitchenArea().compareTo(flat2.getKitchenArea());
    }
}
