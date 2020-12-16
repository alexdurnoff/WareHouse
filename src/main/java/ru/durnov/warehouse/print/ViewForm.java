package ru.durnov.warehouse.print;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;

import java.util.List;
import java.util.Optional;

public class ViewForm {
    private PrintForm printForm;
    private Scene scene;
    private GridPane rootNode;
    private Stage stage;
    private Order order;
    private List<Product> oldProductList;

    public ViewForm(Order order){
        this.order = order;
        this.oldProductList = this.order.getProductList();
        this.printForm = new PrintForm(order);
    }

    public void show(){
        this.rootNode = new GridPane();
        this.scene = new Scene(rootNode);
        this.stage = new Stage();
        stage.setScene(scene);
        Button buttonPrint = new Button("Печать");
        Button buttonClose = new Button("Закрыть");
        Button changeCompanyNameButton = new Button("Сменить поставщика");
        buttonPrint.setOnAction(ae -> {
            try {
                print();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
        buttonClose.setOnAction(ae -> stage.close());
        changeCompanyNameButton.setOnAction(ae -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Изменение названия организации");
            textInputDialog.setContentText("Введите новое название организации");
            Optional<String> optional = textInputDialog.showAndWait();
            if (optional.isPresent()) {
                this.printForm.setCompanyName(optional.get());
                refresh();
            }
        });
        printForm.getGridPane().setAlignment(Pos.CENTER);
        rootNode.add(printForm.getGridPane(), 0,0, 2, 1);
        rootNode.add(buttonPrint, 0,1);
        rootNode.add(buttonClose, 0, 2);
        rootNode.add(changeCompanyNameButton, 0, 3);
        rootNode.setVgap(20);
        rootNode.setPadding(new Insets(10,10,10,10));
        stage.setResizable(false);
        stage.show();
    }

    private void refresh() {
        this.rootNode.getChildren().clear();
        this.printForm = new PrintForm(this.order);
        this.stage.close();
        this.show();
    }

    public void print() throws CloneNotSupportedException {
        stage.close();
        this.printForm.print();
        this.order.setProductList(oldProductList);
    }


}
