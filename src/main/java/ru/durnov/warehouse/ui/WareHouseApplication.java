package ru.durnov.warehouse.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.durnov.warehouse.dao.MockDataBase;
import ru.durnov.warehouse.dao.RealDataBase;
import ru.durnov.warehouse.daoservice.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WareHouseApplication extends Application {
    private GridPane rootNode;
    private ScrollPane scrollPane;
    private EntityDaoService productDao;
    private EntityDaoService storeDao;
    private EntityDaoService orderDao;
    private List<Button> buttonList;
    private OrderArchivPane orderArchivPane;
    private ComboBox<String> comboBox;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Ферма цех");
        setupDao();
        this.rootNode = new GridPane();
        rootNode.setAlignment(Pos.TOP_CENTER);
        rootNode.setHgap(10);
        rootNode.getColumnConstraints().addAll(getColumns());
        this.buttonList = new ArrayList<>();
        addButtons();
        this.orderArchivPane = new OrderArchivPane(this.orderDao);
        rootNode.add(orderArchivPane, 0, 1, 4, 1);
        rootNode.setVgap(20);
        orderArchivPane.show();
        Scene scene = new Scene(rootNode, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupDao() throws SQLException {
        RealDataBase dataBase = new RealDataBase();
        this.productDao = new ProductDaoServiceWithCache(dataBase);
        this.storeDao = new StoreDaoServiceWithCache(dataBase);
        this.orderDao = new OrderDaoServiceWithCache(dataBase);
    }

    private List<ColumnConstraints> getColumns() {
        List<ColumnConstraints> listColumn = new ArrayList<>();
        for (int i = 0; i <4; i++) listColumn.add(new ColumnConstraints());
        return listColumn;
    }

    private void addButtons(){
        List<String> options = new ArrayList<>();
        options.add("База товаров");options.add("База магазинов");
        this.comboBox = new ComboBox<String>(FXCollections.observableList(options));
        this.comboBox.setPromptText("Опции");
        comboBox.setOnAction(ae -> {
            String str = comboBox.getSelectionModel().getSelectedItem();
            if (str.equals("База товаров")) {
                try {
                    showPane(new ProductPane(this.productDao));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (str.equals("База магазинов")){
                try {
                    showPane(new StorePane(this.storeDao));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        Button orderButton = new Button("Создать накладную");
        orderButton.setOnAction(ae -> {
            try {
                showPane(new OrderForm(this.orderDao, this.productDao, this.storeDao));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        rootNode.add(orderButton, 0, 0);
        rootNode.add(comboBox, 2, 0);
        this.buttonList.add(orderButton);
    }

    private void showPane(AbstractPane pane) throws SQLException {
        removeScrollPaneIfExists();
        removeAllButtons();
        int i = 1;
        Button rootButton = new Button("Главное окно");
        this.rootNode.add(rootButton, 0, 0);
        rootButton.setOnAction(ae -> {
            try {
                backToTheStartWindow();
            } catch (SQLException ignored) {

            }
        });
        if (pane.getAddEntityButton() != null){
            pane.getAddEntityButton().setOnAction(ae -> {
                try {
                    pane.addEntityToEntityList();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            this.buttonList.add(pane.getAddEntityButton());
            this.rootNode.add(pane.getAddEntityButton(), i, 0);
            i++;
        }
        if (pane.getClass() == OrderForm.class){
            OrderForm orderForm = (OrderForm) pane;
            Button saveButton = new Button("Сохранить");
            Button printButton = new Button("Печать");
            saveButton.setOnAction(ae -> {
                try {
                    orderForm.save();
                } catch (SQLException ignored) {

                }
            });
            this.rootNode.add(saveButton,i, 0);
            i++;
            printButton.setOnAction(ae -> orderForm.print());
            this.rootNode.add(printButton, i, 0);
            this.buttonList.add(saveButton);
            this.buttonList.add(printButton);
        }
        this.scrollPane = new ScrollPane(pane);
        this.rootNode.add(this.scrollPane, 0, 1, 4, 1);
        pane.show();
    }

    private void backToTheStartWindow() throws SQLException {
        if (this.scrollPane.getContent().getClass() == OrderForm.class){
            OrderForm orderForm = (OrderForm) this.scrollPane.getContent();
            if (! (orderForm.getIsSaved() || orderForm.getDontSave())){
                orderForm.showSavedDialog();
            }
        }
        this.rootNode.getChildren().remove(this.scrollPane);
        removeAllButtons();
        addButtons();
        this.orderArchivPane.refresh();
    }

    private void removeAllButtons() {
        rootNode.getChildren().remove(this.comboBox);
        for (Button button : buttonList) rootNode.getChildren().remove(button);
        this.buttonList = new ArrayList<>();
    }

    public void removeScrollPaneIfExists() {
        if (this.scrollPane != null) this.rootNode.getChildren().remove(scrollPane);
    }

    public static void main(String[] args) {
        launch();
    }

}
