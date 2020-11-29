package ru.durnov.warehouse.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.durnov.warehouse.dao.*;

import java.util.ArrayList;
import java.util.List;

public class WareHouseApplication extends Application {
    private GridPane rootNode;
    private ScrollPane scrollPane;
    private EntityDao productDao;
    private EntityDao storeDao;
    private EntityDao customerDao;
    private EntityDao orderDao;
    private List<Button> buttonList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Программа для работы со складской базой");
        setupDao();
        this.rootNode = new GridPane();
        rootNode.setAlignment(Pos.TOP_CENTER);
        rootNode.setHgap(10);
        rootNode.getColumnConstraints().addAll(getColumns());
        this.buttonList = new ArrayList<>();
        addButtons();
        Scene scene = new Scene(rootNode, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupDao() {
        this.productDao = new DummyProductDaoImpl();
        this.storeDao = new DummyStoreDaoImpl();
        this.customerDao = new DummyCustomerDaoImpl();
        this.orderDao = new DummyOrderDaoImpl();
    }

    private List<ColumnConstraints> getColumns() {
        List<ColumnConstraints> listColumn = new ArrayList<>();
        for (int i = 0; i <4; i++) listColumn.add(new ColumnConstraints());
        return listColumn;
    }

    private void addButtons(){
        Button productButton = new Button("База товаров");
        productButton.setOnAction(ae -> showPane(new ProductPane(this.productDao)));
        Button storeButton = new Button("База магазинов");
        storeButton.setOnAction(ae -> showPane(new StorePane(this.storeDao)));
        Button orderButton = new Button("Создать накладную");
        orderButton.setOnAction(ae -> showPane(new OrderForm()));
        Button orderArchivButton = new Button("Архив накладных");
        orderArchivButton.setOnAction(ae -> showPane(new OrderArchivPane(this.orderDao)));
        rootNode.add(productButton, 0,0);
        rootNode.add(storeButton, 1, 0);
        rootNode.add(orderButton, 2, 0);
        rootNode.add(orderArchivButton, 3, 0);
        this.buttonList.add(productButton);
        this.buttonList.add(storeButton);
        this.buttonList.add(orderButton);
        this.buttonList.add(orderArchivButton);
    }

    private void showPane(AbstractPane pane){
        removeScrollPaneIfExists();
        removeAllButtons();
        int i = 0;
        if (pane.getAddEntityButton() != null){
            pane.getAddEntityButton().setOnAction(ae -> pane.addEntityToEntityList());
            this.rootNode.add(pane.getAddEntityButton(),0, i);
            i++;
        }
        this.scrollPane = new ScrollPane(pane);
        this.rootNode.add(this.scrollPane, 0, i, 4, 1);
        pane.show();
    }

    private void removeAllButtons() {
        for (Button button : buttonList) rootNode.getChildren().remove(button);
    }

    public void removeScrollPaneIfExists() {
        if (this.scrollPane != null) this.rootNode.getChildren().remove(scrollPane);
    }

    public static void main(String[] args) {
        launch();
    }
}