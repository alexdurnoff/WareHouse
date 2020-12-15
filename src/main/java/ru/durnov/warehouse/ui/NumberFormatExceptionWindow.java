package ru.durnov.warehouse.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class NumberFormatExceptionWindow {

    public static void show(){
        FlowPane flowPane = new FlowPane();
        Stage stage = new Stage();
        Scene scene = new Scene(flowPane, 200, 50);
        stage.setScene(scene);
        String message = "Некорректный ввод значения";
        Label label = new Label(message);
        flowPane.getChildren().add(label);
        Button buttonOk = new Button("Ok");
        buttonOk.setOnAction(ae -> stage.close());
        flowPane.getChildren().add(buttonOk);
        stage.show();
    }
}
