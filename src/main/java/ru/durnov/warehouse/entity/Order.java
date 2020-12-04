package ru.durnov.warehouse.entity;

import java.time.LocalDate;
import java.util.*;

public class Order extends Entity {
    private List<Product> productList;
    private final Map<Product,Double> productWeigthMap;
    private final int id;
    private String date;
    private Store store;


    public Order(int id, Store store) {
        super("");
        super.SetTitle("Накладная № " + id);
        this.productList = new ArrayList<>();
        this.productWeigthMap = new HashMap<>();
        this.store = store;
        this.id = id;
        this.date = LocalDate.now().toString();
    }

    public Order(int id, Store store, String date){
        super("");
        super.SetTitle("Накладная № " + id);
        this.productList = new ArrayList<>();
        this.productWeigthMap = new HashMap<>();
        this.store = store;
        this.id = id;
        this.date = date;
    }


    public void addProduct(Product product, Double weigth){
        this.productList.add(product);
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
