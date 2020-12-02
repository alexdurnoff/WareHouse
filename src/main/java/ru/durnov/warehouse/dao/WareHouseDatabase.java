package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;
import ru.durnov.warehouse.entity.StoreProductPair;

import java.util.Set;

public interface WareHouseDatabase {
    public Set<Order> getOrderFromWareHouse();
    public Set<Store> getStoreSet();
    public Set<StoreProductPair> getStoreProductPairSet(String storeName);
    public Set<Product> getProductSet();

    public void addOrderToWareHouse(Order order);
    public void addStoreToDataBase(Store store);
    public void addProductToStore(Store store, Product product);
    public void addProductToWareHouse(Product product);

    public void removeOrder(Order order);
    public void removeProductFromStore(Store store, Product product);
    public void removeStore(Store store);
    public void removeProduct(Product product);

}
