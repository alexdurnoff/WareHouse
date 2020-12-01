package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;

public class StoreCreator extends SimpleEntityEdit{
    private final TextField storeTitle;


    public StoreCreator(StorePane pane) {
        super(pane, new Store(""));
        this.editorTitle = "Добавление магазина";
        this.storeTitle = new TextField("");
        this.button = new Button("Ok");
        button.setOnAction(ae -> {
            setupEntityProrepties(entity);
            pane.entityDao.addEntity(entity);
            pane.refresh();
            stage.close();
        });
    }

    @Override
    protected void setupEntityProrepties(Entity entity) {
        Store store = (Store) entity;
        store.setTitle(storeTitle.getText());
    }

    @Override
    protected void setUpRootNode(GridPane rootNode) {
        Label purposeLabel = new Label("Наименование");
        purposeLabel.setAlignment(Pos.CENTER);
        purposeLabel.setPrefWidth(400);
        rootNode.add(purposeLabel, 0, 0);
        this.storeTitle.setAlignment(Pos.CENTER);
        this.storeTitle.setPrefWidth(400);
        rootNode.add(this.storeTitle, 0, 1);
    }


}
