package ru.durnov.warehouse.entity;

import java.util.Comparator;

public class ProductComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity o1, Entity o2) {
        Product product1 = (Product) o1;
        Product product2 = (Product) o2;
        return String.CASE_INSENSITIVE_ORDER.compare(product1.getTitle(), product2.getTitle());
    }
}
