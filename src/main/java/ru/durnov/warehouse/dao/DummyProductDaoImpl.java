package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Customer;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DummyProductDaoImpl implements EntityDao{
    private List<Entity> productList;

    public DummyProductDaoImpl(){
        this.productList = new ArrayList<>();
        this.productList.add(new Product("Ребро", 355.0, 58.0));
        this.productList.add(new Product("Колбаса", 175.0, 30.0));
        this.productList.add(new Product("Костный мозг", 300.0, 4.0));
        this.productList.add(new Product("Голубцы", 350.0, 5.074));
        this.productList.add(new Product("Кровяная колбаса", 1500.0, 1.0));
    }

    @Override
    public List<Entity> getAllEntity() {
        return productList;
    }

    @Override
    public Entity getEntityByTitle(String title) {
        for (Entity product : productList){
            if (product.getTitle().equals(title)) return product;
        }
        return null;
    }

    @Override
    public Product getEntityById(int id) {
        return (Product) productList.get(id);
    }

    @Override
    public void removeEntityByTitle(String title) {
        for (Entity product : productList){
            if (product.getTitle().equals(title)) productList.remove(product);
        }

    }

    @Override
    public void removeEntityByid(int id) {
        productList.remove(id);
    }

    @Override
    public void addEntity(Entity product) {
        this.productList.add(product);
    }
}
