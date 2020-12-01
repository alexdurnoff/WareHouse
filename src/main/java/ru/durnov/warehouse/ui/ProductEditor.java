package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

public class ProductEditor extends SimpleEntityEdit {
    private final TextField productTitle;
    private final TextField productWeigth;

    public ProductEditor(ProductPane pane, Product product){
        super(pane, product);
        this.editorTitle = "Редактор товара";
        this.productTitle = new TextField(product.getTitle());
        this.productWeigth = new TextField(String.format("%.2f", product.getWeight()));
    }

    @Override
    protected void setupEntityProrepties(Entity entity) {
        Product product = (Product) entity;
        product.setTitle(productTitle.getText());
        product.setWeight(Double.parseDouble(productWeigth.getText().replace(',', '.')));
    }

    @Override
    protected void setUpRootNode(GridPane rootNode) {
        Label purposeLabel = new Label("Наименование");
        purposeLabel.setAlignment(Pos.CENTER);
        purposeLabel.setPrefWidth(400);
        rootNode.add(purposeLabel, 0, 0);
        Label weigthLabel = new Label("Вес");
        weigthLabel.setAlignment(Pos.CENTER);
        weigthLabel.setPrefHeight(40);
        this.productTitle.setAlignment(Pos.CENTER);
        this.productTitle.setPrefWidth(400);
        this.productWeigth.setAlignment(Pos.CENTER);
        this.productWeigth.setPrefWidth(40);
        rootNode.add(weigthLabel, 1, 0);
        rootNode.add(this.productTitle, 0, 1);
        rootNode.add(this.productWeigth, 1, 1);
    }
}
