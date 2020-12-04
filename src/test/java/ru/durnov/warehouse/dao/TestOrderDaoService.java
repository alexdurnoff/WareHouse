package ru.durnov.warehouse.dao;

import org.junit.Test;
import ru.durnov.warehouse.daoservice.OrderDaoService;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TestOrderDaoService {
    @Test
    public void testWhatOrderDaoServiceReturn() throws SQLException {
        OrderDaoService orderDaoService = new OrderDaoService(new RealDataBase());
        List<Entity> orderList = orderDaoService.getAllEntity();
        orderList.forEach(entity -> {
            Order order = (Order) entity;
            List<Product> productList = order.getProductList();
            Map<Product, Double> productDoubleMap = order.getProductWeigthMap();
            productList.forEach(product -> System.out.println(product.getWeight()));
            productDoubleMap.forEach((p,d) -> System.out.println(p + " " + d));
        });
    }

}
