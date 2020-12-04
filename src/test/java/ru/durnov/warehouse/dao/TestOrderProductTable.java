package ru.durnov.warehouse.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Test;
import ru.durnov.warehouse.daoservice.OrderDaoService;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;

import java.sql.SQLException;
import java.util.Set;

public class TestOrderProductTable {

    @Test
    public  void testInsertIntoOrderProductTable() throws SQLException {
        Order order = new Order(300, new Store("Ферма №1"));
        Product product =new Product("Колбаса", 100., 5.4);
        Product product1 = new Product("Ветчина", 200., 2.3);
        order.addProduct(product, product.getWeight());
        order.addProduct(product1, product1.getWeight());
        System.out.println(order.getProductList().size());
        System.out.println(order.getProductWeigthMap());
        OrderDaoService service = new OrderDaoService(new RealDataBase());
        service.addEntity(order);
    }

    @Test
    public void testThatRealDatabaseReturnProductSet() throws SQLException {
        Order order = new Order(300, new Store("Ферма №1"));
        RealDataBase dataBase = new RealDataBase();
        Set<Product> productSet = dataBase.getProductForOrder(order);
        productSet.forEach(System.out::println);
    }
}
