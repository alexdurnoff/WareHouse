package ru.durnov.warehouse.daoservice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.durnov.warehouse.dao.WareHouseDatabase;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;
import ru.durnov.warehouse.entity.StoreProductPair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StoreDaoService implements EntityDaoService {
    private WareHouseDatabase database;

    public StoreDaoService(WareHouseDatabase database){
        this.database = database;
    }
    @Override
    public List<Entity> getAllEntity() throws SQLException {
        ObservableList<Entity> storeList = FXCollections.observableArrayList();
        storeList.addAll(this.database.getStoreSet());
        return storeList;
    }

    @Override
    public Entity getEntityByTitle(String title) throws SQLException {
        Set<Store> storeSet = this.database.getStoreSet();
        for(Store store : storeSet){
            if (store.getTitle().equals(title)) return store;
        }
        return null;
    }

    @Override
    public void removeEntityByTitle(String title) throws SQLException {
        Set<Store> storeSet = this.database.getStoreSet();
        for (Store store : storeSet){
            if (store.getTitle().equals(title)) {
                this.database.removeStore(store);
                break;
            }
        }
    }

    @Override
    public void addEntity(Entity entity) throws SQLException {
        Store store = (Store) entity;
        this.database.addStoreToDataBase(store);
    }

    @Override
    public void updateProduct(Product product) {

    }

    public void addProductToStore(Store store, Product product){
        this.database.addProductToStore(store, product);
    }

    public Set<StoreProductPair> getStoreProductPair(String storeTitle){
        return this.database.getStoreProductPairSet(storeTitle);
    }



}
