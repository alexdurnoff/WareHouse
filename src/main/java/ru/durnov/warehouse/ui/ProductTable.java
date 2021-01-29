package ru.durnov.warehouse.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductTable extends TableView<Product> {
    private final ProductPane productPane;
    private final List<Entity> entityList;
    private ObservableList<Product> productList;


    public ProductTable(ProductPane productPane){
        this.productPane = productPane;
        this.entityList = productPane.getProductList();
        setupProductList();
        TableColumn<Product, Integer> numberColumn = new TableColumn<>("№ п.п.");
        numberColumn.setPrefWidth(50);
        numberColumn.setCellFactory(intColumnCallBack);
        TableColumn<Product,String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setPrefWidth(180);
        nameColumn.setCellFactory(stringColumnCallBack);
        TableColumn<Product,String> uniColumn = new TableColumn<>("кг/шт.");
        uniColumn.setPrefWidth(80);
        uniColumn.setCellFactory(stringColumnCallBack);
        TableColumn<Product,Double> coastColumn = new TableColumn<>("цена");
        coastColumn.setPrefWidth(80);
        coastColumn.setCellFactory(doubleColumnCallBack);
        TableColumn<Product,Button> editColumn = new TableColumn<>("Редактировать продукт");
        editColumn.setPrefWidth(180);
        TableColumn<Product, Button> deleteColumn = new TableColumn<>("Удалить продукт");
        deleteColumn.setPrefWidth(180);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("numberInOrder"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        uniColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        coastColumn.setCellValueFactory(new PropertyValueFactory<>("coast"));
        editColumn.setCellValueFactory(new PropertyValueFactory<>("editButton"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        this.getColumns().addAll(nameColumn, uniColumn, coastColumn, editColumn, deleteColumn);
        this.setItems(this.productList);
        this.setPrefHeight(700);
    }

    private void setupProductList() {
        this.productList = FXCollections.observableList(new ArrayList<>());
        for (Entity entity : entityList){
            if (entity.getClass() == Product.class){
                Product product = (Product) entity;
                product.getDeleteButton().setOnAction(ae-> productPane.removeEntityFromEntityList(product));
                product.getDeleteButton().setPrefWidth(180);
                product.getEditButton().setOnAction(ae-> productPane.editProduct(product));
                product.getEditButton().setPrefWidth(180);
                productList.add(product);
            }
        }
    }

    Callback<TableColumn<Product, String>, TableCell<Product, String>> stringColumnCallBack =
            new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
                @Override
                public TableCell<Product, String> call(TableColumn<Product, String> param) {
                    return new TableCell<Product, String>(){
                        {
                            setAlignment(Pos.CENTER);
                        }
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty){
                                setText("");
                            } else {
                                setText(item);
                            }
                        }
                    };
                }
            };

    Callback<TableColumn<Product,Double>, TableCell<Product,Double>> doubleColumnCallBack =
            new Callback<TableColumn<Product, Double>, TableCell<Product, Double>>() {
                @Override
                public TableCell<Product, Double> call(TableColumn<Product, Double> param) {
                    return new TableCell<Product, Double>(){
                        {
                            setAlignment(Pos.CENTER);
                        }
                        @Override
                        protected void updateItem(Double item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty){
                                setText("");
                            } else {
                                setText(item.toString());
                            }
                        }
                    };
                }
            };

    Callback<TableColumn<Product,Integer>, TableCell<Product,Integer>> intColumnCallBack =
            new Callback<TableColumn<Product, Integer>, TableCell<Product, Integer>>() {
                @Override
                public TableCell<Product, Integer> call(TableColumn<Product, Integer> param) {
                    return new TableCell<Product, Integer>(){
                        {
                            setAlignment(Pos.CENTER);
                        }
                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty){
                                setText("");
                            } else {
                                setText(item.toString());
                            }
                        }
                    };
                }
            };



}
