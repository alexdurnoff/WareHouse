package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Customer;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.util.List;

public interface EntityDao {
    public List<Entity> getAllEntity();
    public Entity getEntityByTitle(String title);
    public Entity getEntityById(int id);
    public void removeEntityByTitle(String title);
    public void removeEntityByid(int id);
    public void addEntity(Entity entity);
}
