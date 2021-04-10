package ru.durnov.warehouse.daoservice;

import javafx.concurrent.Task;
import ru.durnov.warehouse.dao.WareHouseDatabase;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoreDaoServiceWithCache implements EntityDaoService{
    private final WareHouseDatabase database;
    private final Set<Store> storeSet;

    public StoreDaoServiceWithCache(WareHouseDatabase database) {
        Set<Store> storeSet1;
        this.database = database;
        try {
            storeSet1 = database.getStoreSet();
        } catch (SQLException exception) {
            exception.printStackTrace();
            storeSet1 = new HashSet<>();
        }

        this.storeSet = storeSet1;
    }

    @Override
    public List<Entity> getAllEntity() throws SQLException {
        return new ArrayList<>(storeSet);
    }

    @Override
    public Entity getEntityByTitle(String title) throws SQLException {
        for (Store store : storeSet){
            if (store.getTitle().equals(title)) return store;
        }
        return null;
    }

    @Override
    public void removeEntityByTitle(String title) throws SQLException {
        for (Store store : storeSet){
            if (store.getTitle().equals(title)){
                storeSet.remove(store);
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        database.removeStore(store);
                        return null;
                    }
                };
                Thread thread =  new Thread(task);
                thread.setDaemon(false);
                thread.start();
            }
        }

    }

    @Override
    public void addEntity(Entity entity) throws SQLException {
        Store store = (Store) entity;
        this.storeSet.add(store);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                database.addStoreToDataBase(store);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(false);
        thread.start();
    }

    @Override
    public void updateProduct(Product product) {

    }
}
