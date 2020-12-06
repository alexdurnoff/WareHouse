package ru.durnov.warehouse.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private EntityDaoService customerDao;
    private EntityDaoService orderDao;
    private List<Button> buttonList;

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
        Scene scene = new Scene(rootNode, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupDao() throws SQLException {
        RealDataBase dataBase = new RealDataBase();
        this.productDao = new ProductDaoService(dataBase);
        this.storeDao = new StoreDaoService(dataBase);
        this.orderDao = new OrderDaoService(dataBase);
    }

    private List<ColumnConstraints> getColumns() {
        List<ColumnConstraints> listColumn = new ArrayList<>();
        for (int i = 0; i <4; i++) listColumn.add(new ColumnConstraints());
        return listColumn;
    }

    private void addButtons(){
        Button productButton = new Button("База товаров");
        productButton.setOnAction(ae -> {
            try {
                showPane(new ProductPane(this.productDao));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        Button storeButton = new Button("База магазинов");
        storeButton.setOnAction(ae -> {
            try {
                showPane(new StorePane(this.storeDao));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
        Button orderArchivButton = new Button("Архив накладных");
        orderArchivButton.setOnAction(ae -> {
            try {
                showPane(new OrderArchivPane(this.orderDao));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        rootNode.add(productButton, 0,0);
        rootNode.add(storeButton, 1, 0);
        rootNode.add(orderButton, 2, 0);
        rootNode.add(orderArchivButton, 3, 0);
        this.buttonList.add(productButton);
        this.buttonList.add(storeButton);
        this.buttonList.add(orderButton);
        this.buttonList.add(orderArchivButton);
    }

    private void showPane(AbstractPane pane) throws SQLException {
        removeScrollPaneIfExists();
        removeAllButtons();
        int i = 1;
        Button rootButton = new Button("Главное окно");
        this.rootNode.add(rootButton, 0, 0);
        rootButton.setOnAction(ae -> backToTheStartWindow());
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
            saveButton.setOnAction(ae -> orderForm.save());
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

    private void backToTheStartWindow() {
        if (this.scrollPane.getContent().getClass() == OrderForm.class){
            OrderForm orderForm = (OrderForm) this.scrollPane.getContent();
            if (! (orderForm.getIsSaved() || orderForm.getDontSave())){
                orderForm.showSavedDialog();
            }
        }
        this.rootNode.getChildren().remove(this.scrollPane);
        removeAllButtons();
        addButtons();
    }

    private void removeAllButtons() {
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
