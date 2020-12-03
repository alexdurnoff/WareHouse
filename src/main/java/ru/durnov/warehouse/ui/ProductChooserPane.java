package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductChooserPane {
    private final OrderForm orderForm;
    private final List<Entity> productList;

    public ProductChooserPane(OrderForm orderForm){
        this.orderForm = orderForm;
        this.productList = this.orderForm.getProductList();
    }

    public void addEntityToEntityList() {
        GridPane rootNode = new GridPane();
        GridPane productChooser = new GridPane();
        productChooser.setAlignment(Pos.CENTER);
        productChooser.setPrefWidth(800);
        productChooser.setHgap(20);
        ScrollPane scrollPane = new ScrollPane(productChooser);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);
        Scene scene = new Scene(rootNode, 800, 400);
        Stage stage = new Stage();
        stage.setTitle("Добавление товара");
        stage.setScene(scene);
        Set<Product> choosedProductSet = new HashSet<>();
        Button buttonOk = new Button("Ok");
        buttonOk.setOnAction(ae ->{
            choosedProductSet.forEach(p -> this.orderForm.addProduct(p, 0.));
            this.orderForm.refresh();
            stage.close();

        });
        rootNode.add(scrollPane, 0, 1);
        addHeaderProductChooserLine(productChooser);
        int i;
        for (i = 1; i < productList.size() + 1; i++){
            Product product = (Product) productList.get(i-1);
            Label label = new Label(product.getTitle());
            label.setPrefHeight(40);
            label.setPrefWidth(450);
            productChooser.add(label, 0, i +1);
            label = new Label(String.format("%.2f", product.getCoast()));
            label.setAlignment(Pos.CENTER);
            productChooser.add(label, 1, i + 1);
            ProductCheckBox productCheckBox = new ProductCheckBox(product);
            productCheckBox.setOnAction(ae -> choosedProductSet.add(productCheckBox.getProduct()));
            productChooser.add(productCheckBox, 2, i +1);
        }
        i++;
        productChooser.add(buttonOk,0,i+1);
        stage.show();
    }

    private void addHeaderProductChooserLine(GridPane productChooser) {
        Label label = new Label("Наименование товара");
        label.setAlignment(Pos.CENTER);
        productChooser.add(label, 0, 0);
        label = new Label("Стоимость товара");
        label.setAlignment(Pos.CENTER);
        productChooser.add(label, 1, 0);
        label = new Label("Выбрать товар");
        productChooser.add(label, 2, 0);
    }
}
