package ru.durnov.warehouse.daoservice;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import ru.durnov.warehouse.dao.WareHouseDatabase;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;

import java.sql.SQLException;
import java.util.*;

public class OrderDaoServiceWithCache implements EntityDaoService{
    private final WareHouseDatabase database;
    private final Set<Order> orderSet;

    public OrderDaoServiceWithCache(WareHouseDatabase database) {
        Set<Order> orderSet1;
        this.database = database;
        try {
            orderSet1 = database.getOrders();
        } catch (SQLException exception) {
            exception.printStackTrace();
            orderSet1 = new HashSet<>();
        }
        this.orderSet = orderSet1;
        setupOrders();
    }


    @Override
    public List<Entity> getAllEntity() throws SQLException {
        List<Entity> orderList = new ArrayList<>();
        orderList.addAll(orderSet);
        return orderList;
    }

    @Override
    public Entity getEntityByTitle(String title) throws SQLException {
        for (Order order : orderSet){
            if (order.getTitle().equals(title)) return order;
        }
        return null;
    }

    @Override
    public void removeEntityByTitle(String title) throws SQLException {
        for (Order order : orderSet){
            if (order.getTitle().equals(title)) {
                this.orderSet.remove(order);
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        database.removeOrder(order);
                        return null;
                    }
                };
                Thread thread = new Thread(task);
                thread.setDaemon(false);
                thread.start();
            };
        }

    }

    @Override
    public void addEntity(Entity entity) throws SQLException {
        Order order = (Order) entity;
        this.orderSet.add(order);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                database.addOrderToWareHouse(order);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(false);
        thread.start();
    }

    @Override
    public void updateProduct(Product product) {

    }

    public void setupOrders(){
        orderSet.forEach(order -> {
            Set<Product> productSet = null;
            try {
                productSet = this.database.getProductForOrder(order);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            productSet.forEach(product -> order.addProduct(product, product.getWeight()));
        });
    }
}
