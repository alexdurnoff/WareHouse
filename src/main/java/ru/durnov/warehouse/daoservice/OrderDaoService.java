package ru.durnov.warehouse.daoservice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.durnov.warehouse.dao.WareHouseDatabase;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderDaoService implements EntityDaoService {
    private WareHouseDatabase database;

    public OrderDaoService(WareHouseDatabase database){
        this.database = database;
    }
    @Override
    public List<Entity> getAllEntity() {
        ObservableList<Entity> orders = FXCollections.observableArrayList();
        orders.addAll(this.database.getOrders());
        return orders;
    }

    @Override
    public Entity getEntityByTitle(String title) {
        Set<Order> orderSet = this.database.getOrders();
        for (Order order : orderSet){
            if (order.getTitle().equals(title)) return order;
        }
        return null;
    }

    @Override
    public void removeEntityByTitle(String title) {
        Set<Order> orderSet = this.database.getOrders();
        for (Order order : orderSet){
            if (order.getTitle().equals(title)) this.database.removeOrder(order);
        }
    }

    @Override
    public void addEntity(Entity entity) {
        Order order = (Order) entity;
        this.database.addOrderToWareHouse(order);
    }
}
