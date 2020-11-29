package ru.durnov.warehouse.entity;

import java.util.ArrayList;
import java.util.List;

public class Store extends Entity{
    private final List<Product> productList;
    private Customer customer;

    public Store(String title){
        super(title);
        this.productList = new ArrayList<>();
    }

    public Store(String title, Customer customer){
        super(title);
        this.customer = customer;
        this.productList = new ArrayList<>();
    }

    public boolean addProduct(Product product){
        if (this.productList.contains(product)) return false;
        this.productList.add(product);
        return true;
    }

    public String getTitle() {
        return super.getTitle();
    }

    public Customer getCustomer(){return customer;}
}
