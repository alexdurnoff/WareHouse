package ru.durnov.warehouse.daoservice;

import javafx.concurrent.Task;
import ru.durnov.warehouse.dao.WareHouseDatabase;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.sql.SQLException;
import java.util.*;

public class ProductDaoServiceWithCache implements EntityDaoService{
    private final WareHouseDatabase database;
    private final Set<Product> productSet;

    public ProductDaoServiceWithCache(WareHouseDatabase database) {
        Set<Product> productSet1;
        this.database = database;
        try {
            productSet1 = database.getProductSet();
        } catch (SQLException exception) {
            exception.printStackTrace();
            productSet1 = new HashSet<>();
        }
        this.productSet = productSet1;
    }


    @Override
    public List<Entity> getAllEntity() throws SQLException {
        return new ArrayList<>(productSet);
    }

    @Override
    public Entity getEntityByTitle(String title) throws SQLException {
        for (Product product : productSet){
            if (product.getTitle().equals(title)) return product;
        }
        return null;
    }

    @Override
    public void removeEntityByTitle(String title) throws SQLException {
        for (Product product : productSet){
            if (product.getTitle().equals(title)){
                this.productSet.remove(product);
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        database.removeProduct(product);
                        return null;
                    }
                };
                Thread thread = new Thread(task);
                thread.setDaemon(false);
                thread.start();
            }
        }

    }

    @Override
    public void addEntity(Entity entity) throws SQLException {
        Product product = (Product) entity;
        this.productSet.add(product);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                database.addProductToWareHouse(product);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(false);
        thread.start();
    }

    @Override
    public void updateProduct(Product product) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                database.updateProduct(product);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(false);
        thread.start();
    }
}
