package ru.durnov.warehouse.entity;

import java.util.Comparator;

public class ProductNumberComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity o1, Entity o2) {
        Product product1 = (Product) o1;
        Product product2 = (Product) o2;
        return Integer.compare(product1.getNumberInOrder(), product2.getNumberInOrder());
    }
}
