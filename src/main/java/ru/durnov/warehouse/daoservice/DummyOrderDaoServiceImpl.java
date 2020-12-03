package ru.durnov.warehouse.daoservice;

import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DummyOrderDaoServiceImpl implements EntityDaoService {
    private List<Entity> orderList;
    private List<Entity> storeList;

    public DummyOrderDaoServiceImpl() throws SQLException {
        this.orderList = new ArrayList<>();
        this.storeList = new DummyStoreDaoServiceImpl().getAllEntity();
        Order order1 = new Order(1, (Store) this.storeList.get(0));
        orderList.add(order1);
        Order order2 = new Order(2, (Store) this.storeList.get(1));
        orderList.add(order2);
        Order order3 = new Order(3, (Store) this.storeList.get(2));
        orderList.add(order3);
        setupOrders();
    }

    private void setupOrders() throws SQLException {
        EntityDaoService productDao = new DummyProductDaoServiceImpl();
        for (Entity entity : orderList){
            Order order = (Order) entity;
            List<Entity> entityList = productDao.getAllEntity();
            for (int i = 0; i <entityList.size(); i++){
                Product product = (Product) entityList.get(i);
                order.addProduct(product, 1.0);
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
        for (int i = 0; i <this.orderList.size();i++){
            if(this.orderList.get(i).getTitle().equals(title)) this.orderList.remove(i);
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
