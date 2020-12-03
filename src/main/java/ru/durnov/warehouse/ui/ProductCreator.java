package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.sql.SQLException;

public class ProductCreator extends SimpleEntityEdit{
    private final TextField productTitle;
    private final TextField productCoast;

    public ProductCreator(ProductPane pane) {
        super(pane, new Product("", 0.0));
        this.editorTitle = "Добавление товара";
        this.productTitle = new TextField("");
        this.productCoast = new TextField("0.0");
        this.button = new Button("Ok");
        button.setOnAction(ae -> {
            setupEntityProrepties(entity);
            pane.entityDaoService.addEntity(entity);
            try {
                pane.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            stage.close();
        });
    }

    @Override
    protected void setupEntityProrepties(Entity entity) {
        Product product = (Product) entity;
        product.setTitle(productTitle.getText());
        product.setCoast(Double.parseDouble(productCoast.getText().replace(',', '.')));
    }

    @Override
    protected void setUpRootNode(GridPane rootNode) {
        Label purposeLabel = new Label("Наименование");
        purposeLabel.setAlignment(Pos.CENTER);
        purposeLabel.setPrefWidth(400);
        rootNode.add(purposeLabel, 0, 0);
        Label weigthLabel = new Label("Стоимость");
        weigthLabel.setAlignment(Pos.CENTER);
        weigthLabel.setPrefHeight(40);
        this.productTitle.setAlignment(Pos.CENTER);
        this.productTitle.setPrefWidth(400);
        this.productCoast.setAlignment(Pos.CENTER);
        this.productCoast.setPrefWidth(40);
        rootNode.add(weigthLabel, 1, 0);
        rootNode.add(this.productTitle, 0, 1);
        rootNode.add(this.productCoast, 1, 1);
    }


}
