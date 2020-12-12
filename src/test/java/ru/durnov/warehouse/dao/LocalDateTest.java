package ru.durnov.warehouse.dao;


import org.junit.Test;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Store;

import java.time.LocalDate;

public class LocalDateTest {
    @Test
    public void localDateTest(){
        System.out.println(LocalDate.now().toString());
    }

    @Test
    public void constructOrder(){
        Order order1 = new Order(1, new Store("Ферма"), LocalDate.now().toString());
        Order order2 = new Order(1, new Store("Ферма"));
        System.out.println(order1.getDate());
        System.out.println(order2.getDate());
    }
}
