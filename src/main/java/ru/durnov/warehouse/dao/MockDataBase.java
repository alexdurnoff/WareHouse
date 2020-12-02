package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.daoservice.DummyProductDaoServiceImpl;
import ru.durnov.warehouse.daoservice.EntityDaoService;
import ru.durnov.warehouse.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MockDataBase implements WareHouseDatabase{
    private ArrayList<Order> orderTable;
    private ArrayList<Store> storeTable;
    private ArrayList<Product> productTable;
    private ArrayList<StoreProductPair> storeProductTable;

    public MockDataBase(){
        this.orderTable = new ArrayList<>();
        this.storeProductTable = new ArrayList<>();
        this.productTable = new ArrayList<>();
        this.storeTable = new ArrayList<>();

        this.productTable.add(new Product("Ребро", 355.0, 58.0));
        this.productTable.add(new Product("Колбаса", 175.0, 30.0));
        this.productTable.add(new Product("Костный мозг", 300.0, 4.0));
        this.productTable.add(new Product("Голубцы", 350.0, 5.074));
        this.productTable.add(new Product("Кровяная колбаса", 1500.0, 1.0));

        this.storeTable.add(new Store("Ферма №1"));
        this.storeTable.add(new Store("Ферма №2"));
        this.storeTable.add(new Store("Ферма №3"));
        this.storeTable.add(new Store("Ферма №4"));
        
        Order order1 = new Order(1, (Store) this.storeTable.get(0));
        orderTable.add(order1);
        Order order2 = new Order(2, (Store) this.storeTable.get(1));
        orderTable.add(order2);
        Order order3 = new Order(3, (Store) this.storeTable.get(2));
        orderTable.add(order3);
        setupOrders();


    }

    private void setupOrders() {

    }



    @Override
    public Set<Order> getOrderFromWareHouse() {
        return null;
    }

    @Override
    public Set<Store> getStoreSet() {
        return null;
    }

    @Override
    public Set<StoreProductPair> getStoreProductPairSet(String storeName) {
        return null;
    }

    @Override
    public Set<Product> getProductSet() {
        return null;
    }

    @Override
    public void addOrderToWareHouse(Order order) {

    }

    @Override
    public void addStoreToDataBase(Store store) {

    }

    @Override
    public void addProductToStore(Store store, Product product) {

    }

    @Override
    public void addProductToWareHouse(Product product) {

    }

    @Override
    public void removeOrder(Order order) {

    }

    @Override
    public void removeProductFromStore(Store store, Product product) {

    }

    @Override
    public void removeStore(Store store) {

    }

    @Override
    public void removeProduct(Product product) {

    }
}
