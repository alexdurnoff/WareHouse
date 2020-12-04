package ru.durnov.warehouse.daoservice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.durnov.warehouse.dao.WareHouseDatabase;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class OrderDaoService implements EntityDaoService {
    private WareHouseDatabase database;

    public OrderDaoService(WareHouseDatabase database){
        this.database = database;
    }
    @Override
    public List<Entity> getAllEntity() throws SQLException {
        ObservableList<Entity> orders = FXCollections.observableArrayList();
        orders.addAll(this.database.getOrders());
        setupOrders(orders);
        return orders;
    }

    @Override
    public Entity getEntityByTitle(String title) throws SQLException {
        Set<Order> orderSet = this.database.getOrders();
        for (Order order : orderSet){
            if (order.getTitle().equals(title)) return order;
        }
        return null;
    }

    @Override
    public void removeEntityByTitle(String title) throws SQLException {
        Set<Order> orderSet = this.database.getOrders();
        for (Order order : orderSet){
            if (order.getTitle().equals(title)) this.database.removeOrder(order);
        }
    }

    @Override
    public void addEntity(Entity entity) {
        Order order = (Order) entity;
        this.database.addOrderToWareHouse(order);
        this.database.addProductsToOrder(order);
    }

    public void setupOrders(ObservableList<Entity> orders){
        orders.forEach(entity -> {
            Order order = (Order) entity;
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
