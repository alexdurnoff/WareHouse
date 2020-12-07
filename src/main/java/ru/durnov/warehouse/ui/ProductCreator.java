package ru.durnov.warehouse.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.sql.SQLException;

public class ProductCreator extends SimpleEntityEdit{
    private final TextField productTitle;
    private final TextField productCoast;
    private final ComboBox<String> choiceUnit;
    private String unit;

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
        this.choiceUnit = new ComboBox<>();
        this.choiceUnit.getItems().addAll("кг", "шт.");
        this.choiceUnit.setValue("кг");
        this.choiceUnit.setOnAction(ae -> this.unit = choiceUnit.getValue());
    }

    @Override
    protected void setupEntityProrepties(Entity entity) {
        Product product = (Product) entity;
        product.setTitle(productTitle.getText());
        product.setCoast(Double.parseDouble(productCoast.getText().replace(',', '.')));
        product.setUnit(this.unit);
    }

    @Override
    protected void setUpRootNode(GridPane rootNode) {
        Label purposeLabel = new Label("Наименование");
        purposeLabel.setAlignment(Pos.CENTER);
        purposeLabel.setPrefWidth(400);
        rootNode.add(purposeLabel, 0, 0);
        Label unitLabel = new Label("Единица измерения");
        unitLabel.setAlignment(Pos.CENTER);
        Label weigthLabel = new Label("Стоимость");
        weigthLabel.setAlignment(Pos.CENTER);
        weigthLabel.setPrefHeight(40);
        this.productTitle.setAlignment(Pos.CENTER);
        this.productTitle.setPrefWidth(400);
        this.productCoast.setAlignment(Pos.CENTER);
        this.productCoast.setPrefWidth(40);
        rootNode.add(unitLabel, 1, 0);
        rootNode.add(weigthLabel, 2, 0);
        rootNode.add(this.productTitle, 0, 1);
        rootNode.add(this.choiceUnit, 1, 1);
        rootNode.add(this.productCoast, 2, 1);
        rootNode.getChildren().remove(button);
        rootNode.add(button, 3,1);
    }


}
