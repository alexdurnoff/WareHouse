package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Store;

import java.util.ArrayList;
import java.util.List;

public class DummyStoreDaoImpl implements EntityDao {
    private List<Entity> storeList;

    public DummyStoreDaoImpl(){
        this.storeList = new ArrayList<>();
        this.storeList.add(new Store("Ферма №1"));
        this.storeList.add(new Store("Ферма №2"));
        this.storeList.add(new Store("Ферма №3"));
        this.storeList.add(new Store("Ферма №4"));
    }
    @Override
    public List<Entity> getAllEntity() {
        return storeList;
    }

    @Override
    public Entity getEntityByTitle(String title) {
        for (Entity store : storeList){
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
        for ( int i = 0; i < this.storeList.size(); i++){
            if(this.storeList.get(i).getTitle().equals(title)) this.storeList.remove(i);
        }
    }

    @Override
    public void removeEntityByid(int id) {

    }

    @Override
    public void addEntity(Entity store) {
        this.storeList.add(store);
    }
}
