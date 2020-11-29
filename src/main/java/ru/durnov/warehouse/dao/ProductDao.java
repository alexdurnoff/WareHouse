package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Product;

import java.util.List;

public interface ProductDao {
    public List<Product> getAllProducts();
    public Product getProductByTitle(String title);
    public Product getProductById(int id);
    public void removeProductByTitle(String title);
    public void removeProductByid(int id);
    public void addProduct(Product product);

}
