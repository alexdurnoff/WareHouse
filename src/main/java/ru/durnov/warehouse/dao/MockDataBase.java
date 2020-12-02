package ru.durnov.warehouse.dao;

import javafx.scene.input.InputMethodTextRun;
import ru.durnov.warehouse.daoservice.DummyProductDaoServiceImpl;
import ru.durnov.warehouse.daoservice.EntityDaoService;
import ru.durnov.warehouse.entity.*;

import java.util.ArrayList;
import java.util.HashSet;
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
        
        Order order1 = new Order(1, this.storeTable.get(0));
        orderTable.add(order1);
        Order order2 = new Order(2, this.storeTable.get(1));
        orderTable.add(order2);
        Order order3 = new Order(3, this.storeTable.get(2));
        orderTable.add(order3);
        setupOrders();

        setupStoreProductTable();


    }

    private void setupOrders() {

        for (Order order : orderTable) {
            for (Product product : productTable) {
                order.addProduct(product, 5.0);
            }
        }
    }

    private void setupStoreProductTable(){
        for (Store store : storeTable) {
            for (Product product : productTable) {
                StoreProductPair storeProductPair = new StoreProductPair(store, product);
                this.storeProductTable.add(storeProductPair);
            }
        }
    }



    @Override
    public Set<Order> getOrders() {
        Set<Order> orderSet = new HashSet<>();
        for (Order order : orderTable) {
            orderSet.add(order);
        }
        return orderSet;
    }

    @Override
    public Set<Store> getStoreSet() {
        Set<Store> storeSet = new HashSet<>();
        for (Store store : storeTable) {
            storeSet.add(store);
        }
        return storeSet;
    }

    @Override
    public Set<StoreProductPair> getStoreProductPairSet(String storeName) {
        Set<StoreProductPair> storeProductPairSet = new HashSet<>();
        for (StoreProductPair storeProductPair : storeProductTable) {
            storeProductPairSet.add(storeProductPair);
        }
        return storeProductPairSet;
    }

    @Override
    public Set<Product> getProductSet() {
        Set<Product> productSet = new HashSet<>();
        for (Product product : productTable) {
            productSet.add(product);
        }
        return productSet;
    }

    @Override
    public void addOrderToWareHouse(Order order) {
        if (!(orderTable.contains(order)))this.orderTable.add(order);
    }

    @Override
    public void addStoreToDataBase(Store store) {
        if(!(storeTable.contains(store)))this.storeTable.add(store);
    }

    @Override
    public void addProductToStore(Store store, Product product) {
        for (Store store1 : storeTable) {
            if (store1.equals(store)){
                if(store1.getProductList().contains(product)){
                    store1.getProductList().forEach(product1 -> {
                        if(product1.equals(product)){
                            double weigth = product1.getWeight();
                            product1.setWeight(weigth + product.getWeight());
                        }
                    });
                } else {
                    store1.addProduct(product);
                }
            }
        }
    }

    @Override
    public void addProductToWareHouse(Product product) {
        this.productTable.add(product);
    }

    @Override
    public void removeOrder(Order order) {
        orderTable.removeIf(order1 -> order1.equals(order));
    }

    @Override
    public void removeProductFromStore(Store store, Product product) {
        StoreProductPair storeProductPair = new StoreProductPair(store, product);
        storeProductTable.removeIf(productPair -> productPair.equals(storeProductPair));
    }

    @Override
    public void removeStore(Store store) {
        storeTable.removeIf(store1 -> store1.equals(store));
    }

    @Override
    public void removeProduct(Product product) {
        productTable.removeIf(product1 -> product1.equals(product));
    }
}
