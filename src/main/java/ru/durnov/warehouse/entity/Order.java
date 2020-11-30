package ru.durnov.warehouse.entity;

import java.util.*;

public class Order extends Entity {
    private List<Product> productList;
    private Map<Product,Double> productWeigthMap;
    private int number;
    private Date date;


    public Order(int number) {
        super("");
        super.SetTitle("Накладная № " + number);
        this.productList = new ArrayList<>();
        this.productWeigthMap = new HashMap<>();
    }

    public void addProduct(Product product, Double weigth){
        this.productWeigthMap.put(product, weigth);
    }

    public Map<Product, Double> getProductWeigthMap(){
        return productWeigthMap;
    }

}
