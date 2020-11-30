package ru.durnov.warehouse.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ru.durnov.warehouse.entity.Entity;


public class AYouSurePane {
    private String message;
    private AbstractPane pane;
    private Entity entity;
    private Stage stage;
    public AYouSurePane(String message, AbstractPane pane, Entity entity){
        this.message = message;
        this.pane = pane;
        this.entity = entity;
    }

    public void show(){
        FlowPane rootNode = new FlowPane();
        Button buttonOk = new Button("Ok");
        buttonOk.setOnAction(ae -> this.remove());
        Button buttonCancel = new Button("Отмена");
        buttonCancel.setOnAction(ae -> this.cancel());
        this.stage = new Stage();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        Label label = new Label(message);
        rootNode.getChildren().addAll(label, buttonOk, buttonCancel);
        stage.show();
    }

    public void cancel(){
        this.stage.close();
    }

    public void remove(){
        this.pane.removeEntityByTitle(this.entity);
        this.pane.refresh();
        this.stage.close();
    }
}
