package ru.durnov.warehouse.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Before;
import org.junit.Test;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TestRealDataBaseGetOrders {
    private RealDataBase realDataBase;

    @Before
    public void setUp() throws SQLException {
        this.realDataBase = new RealDataBase();
    }

    @Test
    public void testGetOrdersFromDataBase() throws SQLException {
        Set<Order> orderSet = realDataBase.getOrders();
        orderSet.forEach(order -> {
            System.out.println(order.getId());
            System.out.println(order.getStore());
            System.out.println(order.getTitle());
        });
    }

    @Test
    public void testGetStoresFromDataBase() throws SQLException {
        realDataBase.getStoreSet().forEach(System.out::println);
    }

    @Test
    public void testAddOrderToWareHouse() throws SQLException {
        Order order1 = new Order(6, new Store("Ферма №3"));
        Order order2 = new Order(7, new Store("Ферма №2"));
        realDataBase.addOrderToWareHouse(order1);
        realDataBase.addOrderToWareHouse(order2);
    }

    @Test
    public void addStoreToDataBase() throws SQLException {
        Store store = new Store("Ферма №5");
        realDataBase.addStoreToDataBase(store);
    }

    @Test
    public void testAddProductTostoreAndSaveThisToDataBase(){
        Store store = new Store("Ферма №3");
        Product product = new Product("Кровяная колбаса", 355.0);
        product.setWeight(3.45);
        realDataBase.addProductToStore(store, product);
    }

    @Test
    public void testAddProductToWareHouse() throws SQLException {
        Product product = new Product("Курятина", 100.0);
        realDataBase.addProductToWareHouse(product);
    }

    @Test
    public void testRemoveOrder(){
        Order order = new Order(3, new Store("Ферма №1"));
        realDataBase.removeOrder(order);
    }

    @Test
    public void testRemoveProductFromStore(){
        Store store = new Store("Ферма №2");
        Product product = new Product("Курятина", 2.22);
        realDataBase.removeProductFromStore(store, product);
    }

    @Test
    public void testRemoveProduct(){
        Product product = new Product("Голубцы");
        realDataBase.removeProduct(product);
    }

    @Test
    public void testRemoveStore(){
        Store store = new Store("Ферма №5");
        realDataBase.removeStore(store);
    }


}
