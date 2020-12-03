package ru.durnov.warehouse.entity;

import java.util.*;

public class Order extends Entity {
    private List<Product> productList;
    private final Map<Product,Double> productWeigthMap;
    private final int id;
    private Date date;
    private Store store;


    public Order(int id, Store store) {
        super("");
        super.SetTitle("Накладная № " + id);
        this.productList = new ArrayList<>();
        this.productWeigthMap = new HashMap<>();
        this.store = store;
        this.id = id;
    }

    public void addProduct(Product product, Double weigth){
        this.productWeigthMap.put(product, weigth);
    }

    public Map<Product, Double> getProductWeigthMap(){
        return productWeigthMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (! (obj.getClass() == Order.class)) return false;
        Order order = (Order) obj;
        return this.getTitle().equals(order.getTitle());
    }
}
