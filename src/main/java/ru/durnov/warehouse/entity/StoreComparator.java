package ru.durnov.warehouse.entity;

import java.util.Comparator;

public class StoreComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity o1, Entity o2) {
        Store store1 = (Store) o1;
        Store store2 = (Store) o2;
        int number1 = parseNumberStore(store1.getTitle());
        int number2 = parseNumberStore(store2.getTitle());
        //return String.CASE_INSENSITIVE_ORDER.compare(store1.getTitle(), store2.getTitle()); - между прочим, тоже, вроде, работало...
        return Integer.compare(number1, number2);
    }

    private int parseNumberStore(String title) {
        int result;
        try{
            int index = title.indexOf("№");
            String numberStr = title.substring(index + 1);
            result = Integer.parseInt(numberStr);
        } catch(NumberFormatException exception){
            return Integer.MAX_VALUE;
        }
        return result;
    }
}
