package ru.durnov.warehouse.entity;

import java.util.*;

public class Order extends Entity {
    private List<Product> productList;
    private Map<Product,Double> productWeigthMap;
    private int number;
    private Date date;
    private Store store;


    public Order(int number, Store store) {
        super("");
        super.SetTitle("Накладная № " + number);
        this.productList = new ArrayList<>();
        this.productWeigthMap = new HashMap<>();
        this.store = store;
    }

    public void addProduct(Product product, Double weigth){
        this.productWeigthMap.put(product, weigth);
    }

    public Map<Product, Double> getProductWeigthMap(){
        return productWeigthMap;
    }

}
