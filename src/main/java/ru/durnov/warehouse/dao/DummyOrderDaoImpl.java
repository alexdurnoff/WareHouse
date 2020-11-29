package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DummyOrderDaoImpl implements EntityDao {
    private List<Entity> orderList;

    public DummyOrderDaoImpl(){
        this.orderList = new ArrayList<>();
        Order order1 = new Order(1);
        orderList.add(order1);
        Order order2 = new Order(2);
        orderList.add(order2);
        Order order3 = new Order(3);
        orderList.add(order3);
        setupOrders();
    }

    private void setupOrders() {
        EntityDao productDao = new DummyProductDaoImpl();
        for (Entity entity : orderList){
            Order order = (Order) entity;
            List<Entity> entityList = productDao.getAllEntity();
            for (int i = 0; i <entityList.size(); i++){
                Product product = (Product) entityList.get(i);
                order.addProduct(product);
            }
        }
    }


    @Override
    public List<Entity> getAllEntity() {
        return orderList;
    }


    @Override
    public Entity getEntityByTitle(String title) {
        for (Entity order : orderList){
            if (order.getTitle().equals(title)) return order;
        }
        return null;
    }

    @Override
    public Entity getEntityById(int id) {
        return null;
    }

    @Override
    public void removeEntityByTitle(String title) {
        for (Entity order : orderList){
            if (order.getTitle().equals(title)) this.orderList.remove(order);
        }

    }

    @Override
    public void removeEntityByid(int id) {
    }

    @Override
    public void addEntity(Entity entity) {
        this.orderList.add(entity);
    }
}
