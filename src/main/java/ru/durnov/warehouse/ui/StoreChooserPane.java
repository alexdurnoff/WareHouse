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
import java.util.List;

public class StoreChooserPane {
    private final OrderForm orderForm;
    private final List<Entity> storeList;

    public StoreChooserPane(OrderForm orderForm){
        this.orderForm = orderForm;
        this.storeList = orderForm.getStoreList();
    }


    public void setStoreForOrderpane() {
        GridPane rootNode = new GridPane();
        GridPane storeChooser = new GridPane();
        storeChooser.setAlignment(Pos.CENTER);
        storeChooser.setPrefWidth(400);
        storeChooser.setGridLinesVisible(true);
        ScrollPane scrollPane = new ScrollPane(storeChooser);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);
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
        rootNode.add(buttonOk, 0, 0);
        rootNode.add(scrollPane, 0, 1);
        addHeaderProductChooserLine(storeChooser);
        for (int i = 1; i < storeList.size(); i++){
            Store store = (Store) storeList.get(i);
            Label label = new Label(store.getTitle());
            storeChooser.add(label, 0, i+1);
            StoreRadioButton storeRadioButton = new StoreRadioButton(store);
            storeRadioButton.setToggleGroup(toggleGroup);
            if (i == 1) storeRadioButton.setSelected(true);
            storeChooser.add(storeRadioButton, 1, i+1);
        }
        stage.show();
    }

    private void addHeaderProductChooserLine(GridPane storeChooser) {
        Label label = new Label("Наименование магазина");
        storeChooser.add(label, 0, 0);
        label = new Label("Выбрать магазин");
        storeChooser.add(label, 1, 0);
    }
}
