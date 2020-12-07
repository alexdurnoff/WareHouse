package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;
import ru.durnov.warehouse.entity.StoreProductPair;

import java.sql.SQLException;
import java.util.Set;

public interface WareHouseDatabase {
    public Set<Order> getOrders() throws SQLException;
    public Set<Store> getStoreSet() throws SQLException;
    public Set<StoreProductPair> getStoreProductPairSet(String storeName);
    public Set<Product> getProductSet() throws SQLException;
    public Set<Product> getProductForOrder(Order order) throws SQLException;

    public void addOrderToWareHouse(Order order) throws SQLException;
    public void addStoreToDataBase(Store store) throws SQLException;
    public void addProductToStore(Store store, Product product);
    public void addProductToWareHouse(Product product) throws SQLException;

    public void removeOrder(Order order);
    public void removeProductFromStore(Store store, Product product);
    public void removeStore(Store store);
    public void removeProduct(Product product);

    void addProductsToOrder(Order order);
}
