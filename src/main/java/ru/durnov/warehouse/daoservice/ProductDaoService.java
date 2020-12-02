package ru.durnov.warehouse.daoservice;

import ru.durnov.warehouse.dao.WareHouseDatabase;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDaoService implements EntityDaoService {
    private WareHouseDatabase database;

    public ProductDaoService(WareHouseDatabase database){
        this.database = database;
    }
    @Override
    public List<Entity> getAllEntity() {
        ArrayList<Entity> products = new ArrayList<>();
        products.addAll(this.database.getProductSet());
        return products;
    }

    @Override
    public Entity getEntityByTitle(String title) {
        Set<Product> productSet = this.database.getProductSet();
        for (Product product : productSet){
            if (product.getTitle().equals(title)) return product;
        }
        return null;
    }

    @Override
    public void removeEntityByTitle(String title) {
        Set<Product> productSet = this.database.getProductSet();
        for (Product product : productSet){
            if (product.getTitle().equals(title)) {
                this.database.removeProduct(product);
                break;
            }
        }
    }

    @Override
    public void addEntity(Entity entity) {
        Product product = (Product) entity;
        this.database.addProductToWareHouse(product);
    }

}
