package ru.durnov.warehouse.daoservice;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.durnov.warehouse.dao.WareHouseDatabase;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDaoService implements EntityDaoService {
    private final WareHouseDatabase database;

    public ProductDaoService(WareHouseDatabase database){
        this.database = database;
    }
    @Override
    public List<Entity> getAllEntity() throws SQLException {
        ObservableList<Entity> products = FXCollections.observableArrayList();
        products.addAll(this.database.getProductSet());
        return products;
    }

    @Override
    public Entity getEntityByTitle(String title) throws SQLException {
        Set<Product> productSet = this.database.getProductSet();
        for (Product product : productSet){
            if (product.getTitle().equals(title)) return product;
        }
        return null;
    }

    @Override
    public void removeEntityByTitle(String title) throws SQLException {
        Set<Product> productSet = this.database.getProductSet();
        for (Product product : productSet){
            if (product.getTitle().equals(title)) {
                this.database.removeProduct(product);
                break;
            }
        }
    }

    @Override
    public void addEntity(Entity entity) throws SQLException {
        Product product = (Product) entity;
        this.database.addProductToWareHouse(product);
    }

    @Override
    public void updateProduct(Product product) {
        this.database.updateProduct(product);
    }

}
