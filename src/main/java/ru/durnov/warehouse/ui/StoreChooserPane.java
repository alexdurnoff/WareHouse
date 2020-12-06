package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Store;

import java.sql.SQLException;
import java.util.List;

public class StoreChooserPane implements StoreSetupForOrderPane{
    private final OrderForm orderForm;
    private final List<Entity> storeList;

    public StoreChooserPane(OrderForm orderForm) throws SQLException {
        this.orderForm = orderForm;
        this.storeList = orderForm.getStoreList();
    }


    public void setStoreForOrderPane() {
        GridPane rootNode = new GridPane();
        rootNode.setGridLinesVisible(false);
        GridPane storeChooser = new GridPane();
        storeChooser.setAlignment(Pos.CENTER);
        storeChooser.setPrefWidth(450);
        storeChooser.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPane = new ScrollPane(storeChooser);
        scrollPane.setFitToWidth(true);
        //scrollPane.setPrefViewportHeight(380);
        Scene scene = new Scene(rootNode, 450, 400);
        Stage stage = new Stage();
        stage.setTitle("Выбор магазина для накладной");
        stage.setScene(scene);
        ToggleGroup toggleGroup = new ToggleGroup();
        Button buttonOk = new Button("Ok");
        buttonOk.setOnAction(ae ->{
            StoreRadioButton storeRadioButton = (StoreRadioButton) toggleGroup.getSelectedToggle();
            Store store = storeRadioButton.getStore();
            this.orderForm.setStore(store);
            this.orderForm.refresh();
            stage.close();
        });
        rootNode.add(scrollPane, 0, 0);
        addHeaderProductChooserLine(storeChooser);
        int i;
        for (i = 0; i < storeList.size(); i++){
            Store store = (Store) storeList.get(i);
            Label label = new Label(store.getTitle());
            label.setPrefWidth(200);
            label.setPrefHeight(40);
            storeChooser.add(label, 0, i+1);
            StoreRadioButton storeRadioButton = new StoreRadioButton(store);
            storeRadioButton.setToggleGroup(toggleGroup);
            if (i == 1) storeRadioButton.setSelected(true);
            storeChooser.add(storeRadioButton, 1, i+1);
        }
        i++;
        storeChooser.add(buttonOk, 0, i);
        stage.show();
    }

    private void addHeaderProductChooserLine(GridPane storeChooser) {
        Label label = new Label("Наименование магазина");
        label.setPrefHeight(40);
        storeChooser.add(label, 0, 0);
        label = new Label("Выбрать магазин");
        storeChooser.add(label, 1, 0);
    }
}
