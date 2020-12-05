package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;
import ru.durnov.warehouse.entity.StoreProductPair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RealDataBase implements WareHouseDatabase{
    private SqliteDaoConnector connector;

    public RealDataBase() throws SQLException {
        this.connector = new SqliteDaoConnector();
    }

    @Override
    public Set<Order> getOrders() throws SQLException {
        ResultSet resultSet = connector.getResultSet("select * from ordertable");
        Set<Order> orderSet = new HashSet<>();
        while (resultSet.next()){
            String idStr = resultSet.getString(1);
            int id = Integer.parseInt(idStr);
            String storeStr = resultSet.getString(2);
            Store store = new Store(storeStr);
            String date = resultSet.getString(3);
            orderSet.add(new Order(id, store, date));
        }
        return orderSet;
    }

    @Override
    public Set<Store> getStoreSet() throws SQLException {
        ResultSet resultSet = connector.getResultSet("select * from storetable");
        Set<Store> storeSet = new HashSet<>();
        while (resultSet.next()){
            storeSet.add(new Store(resultSet.getString(1)));
        }
        return storeSet;
    }

    @Override
    public Set<StoreProductPair> getStoreProductPairSet(String storeName) {
        return null;
    }

    @Override
    public Set<Product> getProductSet() throws SQLException {
        ResultSet resultSet = connector.getResultSet("select * from producttable");
        Set<Product> productSet = new HashSet<>();
        while (resultSet.next()){
            String title = resultSet.getString("product");
            double coast = Double.parseDouble(resultSet.getString(2));
            productSet.add(new Product(title,coast));
        }
        return productSet;
    }

    /**
     * Ну не работает по человечески!! Не хочет делать select product, weigth where order='...
     * Как я только не изгалялся. syntax error near order, и все. Хот ты пляши вокруг него!!!
     * @param order
     * @return
     * @throws SQLException
     */
    @Override
    public Set<Product> getProductForOrder(Order order) throws SQLException {
        String request = "select * from orderproducttable";
        ResultSet resultSet = connector.getResultSet(request);
        Set<Product> productSet = new HashSet<>();
        while (resultSet.next()){
            String orderStr = resultSet.getString(1);
            String productTitle = resultSet.getString(2);
            double weight = resultSet.getDouble(3);
            double coast = resultSet.getDouble(4);
            int productNumber = resultSet.getInt(5);
            System.out.println(weight);
            if (orderStr.equals(order.getTitle())) productSet.add(new Product(productTitle,coast, weight, productNumber));
        }
        return productSet;
    }

    @Override
    public void addOrderToWareHouse(Order order) {
        String firstStr = "insert into ordertable values(";
        String id = String.valueOf(order.getId());
        String store = '"' + order.getStore().toString() + '"';
        String date = '"' + order.getDate() + '"';
        String request = firstStr + id + ", " + store + ", " + date + ");";
        connector.executeUpdateRequest(request);
    }

    @Override
    public void addStoreToDataBase(Store store) {
        String request = "insert into storetable values(" + '"' + store.getTitle() + '"' + ");";
        connector.executeUpdateRequest(request);
    }

    @Override
    public void addProductToStore(Store store, Product product) {
        String request = "insert into storeproducttable values(" + '"' + store.getTitle() + '"' + ", " + '"'+
                product.getTitle() + '"' + ", " + product.getWeight() + ");";
        connector.executeUpdateRequest(request);
    }

    @Override
    public void addProductToWareHouse(Product product) {
        String request = "insert into producttable values(" + '"' + product.getTitle() + '"' +
                ", " + '"' + product.getCoast() + '"' + ");";
        connector.executeUpdateRequest(request);
    }

    @Override
    public void addProductsToOrder(Order order) {
        Map<Product, Double> productWeigthMap = order.getProductWeigthMap();
        productWeigthMap.forEach(((product, weigth) -> {
            String request = "insert into orderproducttable values(" + '"' + order.getTitle() + '"' +
                    ", " + '"' + product.getTitle() + '"' + ", " + weigth + ", " + product.getCoast() + ", " + product.getNumberInOrder()+ ");";
            this.connector.executeUpdateRequest(request);
        }));
    }

    @Override
    public void removeOrder(Order order) {
        String request = "delete from ordertable where id='" + order.getId() + "';";
        connector.executeUpdateRequest(request);
        String title = order.getTitle();
        request = "delete from orderproducttable where titleoforder='" + title + "';";
        connector.executeUpdateRequest(request);
    }

    @Override
    public void removeProductFromStore(Store store, Product product) {
        String request = "delete from storeproducttable where store='"+
                store.getTitle() + "'"+ " and " + "product='" + product.getTitle() + "';";
        connector.executeUpdateRequest(request);
    }

    @Override
    public void removeStore(Store store) {
        String request = "delete from storetable where title='" + store.getTitle() + "'";
        connector.executeUpdateRequest(request);
    }

    @Override
    public void removeProduct(Product product) {
        String request = "delete from producttable where product='" + product.getTitle() + "'";
        connector.executeUpdateRequest(request);
    }


}
