package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Store;

public class StoreEditor extends SimpleEntityEdit{
    private TextField textField;

    public StoreEditor(StorePane pane, Store store){
        super(pane, store);
        this.editorTitle = "Редактор магазина";
    }


    @Override
    protected void setupEntityProrepties(Entity entity) {
        entity.setTitle(textField.getText());
    }

    @Override
    protected void setUpRootNode(GridPane rootNode) {
        this.textField = new TextField(this.entity.getTitle());
        textField.setAlignment(Pos.CENTER);
        this.textField.setPrefWidth(400);
        Label purposeLabel = new Label("Наименование");
        purposeLabel.setAlignment(Pos.CENTER);
        purposeLabel.setPrefWidth(400);
        rootNode.add(purposeLabel, 0, 0);
        rootNode.add(this.textField, 0, 1);
    }

}
