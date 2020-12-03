package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.durnov.warehouse.entity.Entity;

import java.sql.SQLException;

/**
 * Abstract class for edit store and product entities
 * For edit Order entity is used OrderForm.class
 */
public abstract class SimpleEntityEdit {
    protected Entity entity;
    protected AbstractPane pane;
    protected Stage stage;
    protected String editorTitle;
    protected Button button;
    
    protected SimpleEntityEdit(AbstractPane pane, Entity entity){
        this.pane = pane;
        this.entity = entity;
        this.button = new Button("Ok");
        button.setOnAction(ae -> {
            setupEntityProrepties(entity);
            try {
                pane.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            stage.close();
        });
    }
    
    public void show(){
        GridPane rootNode = new GridPane();
        rootNode.setAlignment(Pos.CENTER);
        rootNode.setHgap(20);
        this.stage = new Stage();
        Scene scene = new Scene(rootNode, 800,100);
        stage.setScene(scene);
        stage.setTitle(editorTitle);
        rootNode.add(button,2, 1);
        setUpRootNode(rootNode);
        stage.show();
    }

    protected abstract void setupEntityProrepties(Entity entity);

    protected abstract void setUpRootNode(GridPane rootNode);
}
