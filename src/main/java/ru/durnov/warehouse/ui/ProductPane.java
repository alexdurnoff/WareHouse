package ru.durnov.warehouse.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.daoservice.EntityDaoService;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.ProductComparator;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ProductPane extends AbstractPane {
    private List<Entity> productList;
    private ProductTable productTable;

    public ProductPane(EntityDaoService productDao){
        super();
        super.setEntityButtonTitle("Добавить товар");
        this.entityDaoService = productDao;
        this.message = "Вы уверены, что хотите удалить этот продукт?";
        this.removeEntityMessage = "Удалить продукт";
        this.setPrefHeight(700);
    }

    @Override
    public void addEntityToEntityList() throws SQLException {
        constructNewProduct();
        this.productList = entityDaoService.getAllEntity();
    }

    private void constructNewProduct() {
        new ProductCreator(this).show();
    }

    @Override
    public void show() throws SQLException {
        this.productList = entityDaoService.getAllEntity();
        this.productList.sort(new ProductComparator());
        this.productTable = new ProductTable(this);
        this.add(productTable, 0, 0);
    }

    public void editProduct(Product product) {
        new ProductEditorDialog(product).showAndWait();
        this.entityDaoService.updateProduct(product);
        this.getChildren().remove(this.productTable);
        this.productTable = new ProductTable(this);
        this.add(productTable, 0, 0);
    }

    public List<Entity> getProductList() {
        return productList;
    }
}
