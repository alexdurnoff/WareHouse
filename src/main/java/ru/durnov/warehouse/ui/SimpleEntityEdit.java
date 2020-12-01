package ru.durnov.warehouse.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.durnov.warehouse.entity.Entity;

/**
 * Abstract class for edit store and product entities
 * For edit Order entity is used OrderForm.class
 */
public abstract class SimpleEntityEdit {
    protected Entity entity;
    private AbstractPane pane;
    private Stage stage;
    protected String editorTitle;
    private Button buttonOk;
    
    protected SimpleEntityEdit(AbstractPane pane, Entity entity){
        this.pane = pane;
        this.entity = entity;
    }
    
    public void show(){
        GridPane rootNode = new GridPane();
        rootNode.setAlignment(Pos.CENTER);
        rootNode.setHgap(20);
        this.stage = new Stage();
        Scene scene = new Scene(rootNode, 800,100);
        stage.setScene(scene);
        stage.setTitle(editorTitle);
        this.buttonOk = new Button("Ok");
        buttonOk.setOnAction(ae -> {
            setupEntityProrepties(entity);
            pane.refresh();
            stage.close();
        });
        rootNode.add(buttonOk,2, 1);
        setUpRootNode(rootNode);
        stage.show();
    }

    protected abstract void setupEntityProrepties(Entity entity);

    protected abstract void setUpRootNode(GridPane rootNode);
}
