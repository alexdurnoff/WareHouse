package ru.durnov.warehouse.print;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.ProductWrapper;

import java.util.ArrayList;
import java.util.List;

public class PrintForm {
    private Order order;
    private GridPane gridPane;
    private TableView<ProductWrapper> tableView;

    public PrintForm(Order order) {
        this.order = order;
        this.gridPane = new GridPane();
        this.tableView = new TableView<>();
        setupTableView();
        setupGridPane();
    }

    private void setupTableView() {
        this.tableView.setPrefWidth(400);
        this.tableView.setMaxWidth(400);
        this.tableView.setMinWidth(400);
        ObservableList<ProductWrapper> productWrappers = getProductWrapperList();
        TableColumn<ProductWrapper,Integer> numberColumn = new TableColumn<>("№ п.п");
        TableColumn<ProductWrapper,String> titleColumn = new TableColumn<>("Наименование");
        TableColumn<ProductWrapper,String> countColumn = new TableColumn<>("К-во");
        TableColumn<ProductWrapper,String> coastColumn = new TableColumn<>("Цена");
        TableColumn<ProductWrapper,String> sumColumn = new TableColumn<>("Сумма");
        this.tableView.getColumns().addAll(numberColumn, titleColumn, countColumn, coastColumn, sumColumn);
    }

    private ObservableList<ProductWrapper> getProductWrapperList() {
        List<Product> productList = this.order.getProductList();
        List<ProductWrapper> productWrapperList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++){
            productWrapperList.add(new ProductWrapper(productList.get(i), i + 1));
        }
        return FXCollections.observableList(productWrapperList);
    }

    private void setupGridPane() {
        Label dateLabel = new Label(this.order.getDate());
        dateLabel.setAlignment(Pos.BASELINE_RIGHT);
        Label orderTitleLabel = new Label(this.order.getTitle());
        orderTitleLabel.setAlignment(Pos.BASELINE_CENTER);
        Label toLeftLabel = new Label("Кому");
        toLeftLabel.setAlignment(Pos.BASELINE_RIGHT);
        toLeftLabel.setPrefWidth(40);
        Label toRightLabel = new Label(this.order.getStore().toString());
        toRightLabel.setAlignment(Pos.BASELINE_LEFT);
        Label fromLeftLabel = new Label("От");
        fromLeftLabel.setAlignment(Pos.BASELINE_RIGHT);
        Label fromRightLabel = new Label("ИП Соловьев");
        fromRightLabel.setAlignment(Pos.BASELINE_LEFT);
        Label producerLabel = new Label("Сдал");
        Label consumerLabel = new Label("Принял");
        this.gridPane.add(dateLabel, 0, 0, 3, 1);
        this.gridPane.add(orderTitleLabel, 1, 1, 3, 1);
        this.gridPane.add(toLeftLabel, 0, 2);
        this.gridPane.add(toRightLabel, 1, 2);
        this.gridPane.add(fromLeftLabel, 0, 3);
        this.gridPane.add(fromRightLabel, 1, 3);
        this.gridPane.add(this.tableView, 0, 4, 3, 1);
        this.gridPane.add(producerLabel, 0,5);
        this.gridPane.add(consumerLabel, 2, 5);
    }

    public void print() {
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
