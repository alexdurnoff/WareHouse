package ru.durnov.warehouse.entity;

import java.util.Comparator;

public class StoreComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity o1, Entity o2) {
        Store store1 = (Store) o1;
        Store store2 = (Store) o2;
        return String.CASE_INSENSITIVE_ORDER.compare(store1.getTitle(), store2.getTitle());
    }
}
