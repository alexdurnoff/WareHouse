package ru.durnov.warehouse.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.dao.EntityDao;
import ru.durnov.warehouse.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPane extends GridPane {
    private List<Entity> entityList;
    protected EntityDao entityDao;
    private Button addEntityButton;
    protected String message;
    protected String removeEntityMessage;

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

    public void addEntityToEntityList(){

    };

    public abstract void show();

    public void removeEntityByTitle(Entity entity){
        this.entityDao.removeEntityByTitle(entity.getTitle());
        this.entityList.remove(entity);
    }

    public void refresh(){
        this.getChildren().clear();
        this.show();
    }

    protected class RemoveEntityButton extends Button{
        private Entity entity;

        RemoveEntityButton(Entity entity){
            super(removeEntityMessage);
            this.entity = entity;
            this.setOnAction(ae -> removeEntityFromEntityList(this.entity));
        }
    }

    protected void removeEntityFromEntityList(Entity entity){
        new AYouSurePane(message, this, entity).show();
        this.entityList = this.entityDao.getAllEntity();
    };
}
