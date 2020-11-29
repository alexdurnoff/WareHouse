package ru.durnov.warehouse.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order extends Entity {
    private List<Product> productList;
    private int number;
    private Date date;


    public Order(int number) {
        super("");
        super.SetTitle("Накладная № ");
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product){
        this.productList.add(product);
    }


}
