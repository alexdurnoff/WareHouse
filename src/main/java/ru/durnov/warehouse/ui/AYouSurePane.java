package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ru.durnov.warehouse.entity.Entity;

import java.sql.SQLException;


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
        rootNode.setAlignment(Pos.CENTER);
        rootNode.setHgap(20);
        Button buttonOk = new Button("Ok");
        buttonOk.setOnAction(ae -> {
            try {
                this.remove();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        Button buttonCancel = new Button("Отмена");
        buttonCancel.setOnAction(ae -> this.cancel());
        this.stage = new Stage();
        Scene scene = new Scene(rootNode, 500, 50);
        stage.setScene(scene);
        Label label = new Label(message);
        rootNode.getChildren().addAll(label, buttonOk, buttonCancel);
        stage.show();
    }

    public void cancel(){
        this.stage.close();
    }

    public void remove() throws SQLException {
        this.pane.removeEntityByTitle(this.entity);
        this.pane.refresh();
        this.stage.close();
    }
}
