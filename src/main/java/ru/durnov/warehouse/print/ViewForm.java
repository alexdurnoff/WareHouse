package ru.durnov.warehouse.print;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.durnov.warehouse.entity.Order;

public class ViewForm {
    private final PrintForm printForm;

    public ViewForm(Order order){
        this.printForm = new PrintForm(order);
    }

    public void show(){
        GridPane rootNode = new GridPane();
        Scene scene = new Scene(rootNode, 430, 640);
        Stage stage = new Stage();
        stage.setScene(scene);
        Button buttonPrint = new Button("Печать");
        Button buttonClose = new Button("Закрыть");
        buttonPrint.setOnAction(ae -> this.printForm.print());
        buttonClose.setOnAction(ae -> stage.close());
        printForm.getGridPane().setAlignment(Pos.CENTER);
        rootNode.add(printForm.getGridPane(), 0,0, 2, 1);
        rootNode.add(buttonPrint, 0,1);
        rootNode.add(buttonClose, 0, 2);
        rootNode.setVgap(20);
        rootNode.setPadding(new Insets(10,10,10,10));
        stage.setResizable(false);
        stage.show();
    }
}
