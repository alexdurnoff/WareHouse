package ru.durnov.warehouse.entity;

public class ProductWrapper {
    private Product product;
    private Integer id;

    public ProductWrapper(Product product, int id){
        this.product = product;
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getId() {
        return id;
    }
}
