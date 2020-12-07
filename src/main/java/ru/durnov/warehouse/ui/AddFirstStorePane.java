package ru.durnov.warehouse.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.durnov.warehouse.daoservice.EntityDaoService;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Store;

import java.sql.SQLException;

public class AddFirstStorePane extends Application {
    private EntityDaoService service;
    private Stage stage;


    public AddFirstStorePane(OrderForm pane) {
       this.service = pane.entityDaoService;
    }

    public void show(){
        launch();
    }

    protected void setupEntityProrepties(Store store) throws SQLException {
        this.service.addEntity(store);
        stage.close();
    }

    public boolean isVisible(){
        return this.stage.isShowing();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane pane = new GridPane();
        Scene scene = new Scene(pane, 200, 200);
        this.stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Необходимо добавить первый магазин в базу");
        Button buttonOk = new Button("Ok");
        buttonOk.setOnAction(ae -> {
            try {
                setupEntityProrepties(new Store(""));
            } catch (SQLException ignored) {

            }
        });
        Label label1 = new Label("Перед созданием накладной необходимо добавить ");
        label1.setPrefWidth(400);
        label1.setAlignment(Pos.CENTER);
        Label label2 = new Label("хотя бы один магазин в базу данных");
        label2.setPrefWidth(400);
        label2.setAlignment(Pos.CENTER);
        Label label3 = new Label("Введите название магазина");
        label3.setPrefWidth(400);
        label3.setAlignment(Pos.CENTER);
        TextField textField = new TextField();
        pane.add(label1, 0, 0);
        pane.add(label2, 0, 1);
        pane.add(label3, 0, 2);
        pane.add(textField, 0, 3);
        pane.add(buttonOk, 0, 4);
        stage.show();
    }
}
