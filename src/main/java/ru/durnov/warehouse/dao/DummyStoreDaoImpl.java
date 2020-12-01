package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Store;

import java.util.ArrayList;
import java.util.List;

public class DummyStoreDaoImpl implements EntityDao {
    private List<Entity> entityList;

    public DummyStoreDaoImpl(){
        this.entityList = new ArrayList<>();
        this.entityList.add(new Store("Ферма №1"));
        this.entityList.add(new Store("Ферма №2"));
        this.entityList.add(new Store("Ферма №3"));
        this.entityList.add(new Store("Ферма №4"));
    }
    @Override
    public List<Entity> getAllEntity() {
        return entityList;
    }

    @Override
    public Entity getEntityByTitle(String title) {
        for (Entity store : entityList){
            if (store.getTitle().equals(title)) return store;
        }
        return null;
    }

    @Override
    public Store getEntityById(int id) {
        return null;
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

    }

    @Override
    public void addEntity(Entity store) {
        this.entityList.add(store);
    }
}
