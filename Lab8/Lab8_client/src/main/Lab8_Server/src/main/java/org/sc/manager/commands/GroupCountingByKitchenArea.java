package org.sc.manager.commands;

import org.sc.Request;
import org.sc.data.Flat;
import org.sc.data.comparators.FlatsKitchenComparator;
import org.sc.manager.CollectionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/**
 *  Данная команда сгрупировывает элементы коллекции по значению поля kitchenArea, выводит количество элементов в каждой группе
 *
 * @author Kemansu
 * @since 1.0
 */

public class GroupCountingByKitchenArea implements Command{
    @Override
    public String execute(Request request) throws Exception {
        // Начало
        List<Flat> arrayDeque_list = CollectionManager.getArrayDeque().stream().toList();
        ArrayList<Flat> arrayList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        // Считываем массив
        for (Flat flat : arrayDeque_list){
            arrayList.add(flat);
        }

        // Сортировка
        Collections.sort(arrayList, new FlatsKitchenComparator());

        // Задаем необходимые логические переменные
        HashMap<String, Integer> counts = new HashMap<>();
        Integer counter = 1;



        // прогоняем все через цикл
        for (int i = 0; i < arrayList.size() - 1; i++) {
            if (arrayList.get(i).getKitchenArea().equals(arrayList.get(i + 1).getKitchenArea())){
                counter++;
                if (arrayList.get(i + 1) == arrayList.get(arrayList.size() - 1)) {
                    counts.put(String.valueOf(arrayList.get(i).getKitchenArea()), counter);
                }
            } else {
                counts.put(String.valueOf(arrayList.get(i).getKitchenArea()), counter);
                if (arrayList.get(i + 1) == arrayList.get(arrayList.size() - 1)) {
                    counts.put(String.valueOf(arrayList.get(i + 1).getKitchenArea()), 1);
                }
                counter = 1;
            }
        }

        // Выводим результаты
        for (String key : counts.keySet()){
            stringBuilder.append("Kitchen area: " + key + "; Amount: " + counts.get(key) + "\n");
        }

        if (CollectionManager.getArrayDeque().size() == 1) {
            stringBuilder.append("Kitchen area: " + arrayList.get(0).getKitchenArea() + "; Amount: " + "1 ");
        }
        if (CollectionManager.getArrayDeque().size() == 0) {
            return "no flats in collection :(";
        }
        return stringBuilder.toString();
    }

    @Override
    public String getName() {
        return "group_counting_by_kitchen_area";
    }

    @Override
    public String getDescription() {
        return "сгруппировать элементы коллекции по значению поля kitchenArea, вывести количество элементов в каждой группе";
    }
}
