package ru.durnov.warehouse.daoservice;

import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;

import java.sql.SQLException;
import java.util.List;

public interface EntityDaoService {
    public List<Entity> getAllEntity() throws SQLException;
    public Entity getEntityByTitle(String title) throws SQLException;
    public default Entity getEntityById(int id){
        return null;
    };
    public void removeEntityByTitle(String title) throws SQLException;
    public default void removeEntityByid(int id){

    };
    public void addEntity(Entity entity) throws SQLException;
    public default void addStoreProductpair(Store store, Product product){

    }
}
