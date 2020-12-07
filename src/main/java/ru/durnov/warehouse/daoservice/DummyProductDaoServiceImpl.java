package ru.durnov.warehouse.daoservice;

import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DummyProductDaoServiceImpl implements EntityDaoService {
    private List<Entity> entityList;

    public DummyProductDaoServiceImpl(){
        this.entityList = new ArrayList<>();
        this.entityList.add(new Product("Ребро", 355.0, 58.0));
        this.entityList.add(new Product("Колбаса", 175.0, 30.0));
        this.entityList.add(new Product("Костный мозг", 300.0, 4.0));
        this.entityList.add(new Product("Голубцы", 350.0, 5.074));
        this.entityList.add(new Product("Кровяная колбаса", 1500.0, 1.0));
    }

    @Override
    public List<Entity> getAllEntity() {
        return entityList;
    }

    @Override
    public Entity getEntityByTitle(String title) {
        for (Entity product : entityList){
            if (product.getTitle().equals(title)) return product;
        }
        return null;
    }

    @Override
    public Product getEntityById(int id) {
        return (Product) entityList.get(id);
    }

    @Override
    public void removeEntityByTitle(String title) {
        //int size = this.entityList.size();
        for ( int i = 0; i < this.entityList.size(); i++){
            if(this.entityList.get(i).getTitle().equals(title)) this.entityList.remove(i);
        }
    }

    @Override
    public void removeEntityByid(int id) {
        entityList.remove(id);
    }

    @Override
    public void addEntity(Entity product) {
        this.entityList.add(product);
    }

    @Override
    public void updateProduct(Product product) {

    }
}
