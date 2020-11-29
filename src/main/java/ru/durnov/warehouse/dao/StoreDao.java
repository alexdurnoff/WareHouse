package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Store;

import java.util.List;

public interface StoreDao {
    public List<Store> getAllStores();
    public Store getStoreByTitle(String title);
    public Store getStoreById(int id);
    public void RemoveStoreByTitle(String title);
    public void addStore(Store store);
}
