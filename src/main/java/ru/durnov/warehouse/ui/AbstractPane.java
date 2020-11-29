package ru.durnov.warehouse.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPane extends GridPane {
    private List<Entity> entityList;
    private Button addEntityButton;

    protected AbstractPane() {
        this.entityList = new ArrayList<>();
        this.addEntityButton = new Button();
    }

    protected void setEntityButtonTitle(String title){
        this.addEntityButton.setText(title);
    }

    public Button getAddEntityButton(){
        return this.addEntityButton;
    }

    public void setAddEntityButton(Button button){
        this.addEntityButton = button;
    }

    public abstract void addEntityToEntityList();

    public abstract void show();
}
