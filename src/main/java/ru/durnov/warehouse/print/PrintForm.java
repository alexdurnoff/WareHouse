package ru.durnov.warehouse.print;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.ProductWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrintForm {
    private final Order order;
    private final GridPane gridPane;
    private final TableView<ProductWrapper> tableView;

    public PrintForm(Order order) {
        this.order = order;
        this.gridPane = new GridPane();
        //this.gridPane.setPrefWidth(420);
        //this.getGridPane().setPrefHeight(594);
        this.tableView = new TableView<>();
        setupTableView();
        setupGridPane();
    }

    private void setupTableView() {
        /*this.tableView.setPrefWidth(402);
        this.tableView.setMaxWidth(402);
        this.tableView.setMinWidth(402);*/
        ObservableList<ProductWrapper> productWrappers = getProductWrapperList();
        TableColumn<ProductWrapper,Integer> numberColumn = new TableColumn<>("№ п.п");
        numberColumn.setPrefWidth(50);
        TableColumn<ProductWrapper,String> titleColumn = new TableColumn<>("Наименование");
        titleColumn.setPrefWidth(180);
        TableColumn<ProductWrapper,String> countColumn = new TableColumn<>("К-во");
        countColumn.setPrefWidth(50);
        TableColumn<ProductWrapper,String> coastColumn = new TableColumn<>("Цена");
        coastColumn.setPrefWidth(60);
        TableColumn<ProductWrapper,String> sumColumn = new TableColumn<>("Сумма");
        sumColumn.setPrefWidth(60);
        this.tableView.getColumns().addAll(numberColumn, titleColumn, countColumn, coastColumn, sumColumn);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        //countColumn.setCellValueFactory(new PropertyValueFactory<>("weigth"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("convert"));
        coastColumn.setCellValueFactory(new PropertyValueFactory<>("coast"));
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
        this.tableView.setItems(productWrappers);
    }

    private ObservableList<ProductWrapper> getProductWrapperList() {
        List<Product> productList = this.order.getProductList();
        Map<Product, Double> productWeigthMap = order.getProductWeigthMap();
        List<ProductWrapper> productWrapperList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++){
            ProductWrapper productWrapper = new ProductWrapper(productList.get(i), i + 1, 0, 0);
            productWeigthMap.forEach(((product, weigth) -> {
                if (productWrapper.getProduct().equals(product)){
                    productWrapper.setWeigth(product.getWeight());
                    productWrapper.setCoast(product.getCoast());
                    System.out.println(productWrapper.getWeight());
                }
            } ));
            productWrapperList.add(productWrapper);
        }
        return FXCollections.observableList(productWrapperList);
    }

    private void setupGridPane() {
        Label dateLabel = new Label(this.order.getDate());
        dateLabel.setAlignment(Pos.BASELINE_RIGHT);
        dateLabel.setPrefWidth(400);
        Label orderTitleLabel = new Label(this.order.getTitle());
        orderTitleLabel.setAlignment(Pos.BASELINE_CENTER);
        orderTitleLabel.setPrefWidth(400);
        orderTitleLabel.setFont(new Font(16));
        Label toLeftLabel = new Label("Кому");
        toLeftLabel.setAlignment(Pos.BASELINE_LEFT);
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
        this.gridPane.add(orderTitleLabel, 0, 1,3,1);
        this.gridPane.add(toLeftLabel, 0, 2);
        this.gridPane.add(toRightLabel, 1, 2);
        this.gridPane.add(fromLeftLabel, 0, 3);
        this.gridPane.add(fromRightLabel, 1, 3);
        this.gridPane.add(this.tableView, 0, 4, 3, 1);
        this.gridPane.add(producerLabel, 0,5);
        this.gridPane.add(consumerLabel, 2, 5);
        this.getGridPane().setVgap(10);
    }

    public void print() {
        javafx.print.PrinterJob job = PrinterJob.createPrinterJob(Printer.getDefaultPrinter());
        JobSettings jobSettings = job.getJobSettings();
        PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(Paper.A4,
                PageOrientation.PORTRAIT, 20,10,10,10);
        jobSettings.setPageLayout(pageLayout);
        double scaleX = 1.3;
        double scaleY = 1.3;
        /*double scaleX = pageLayout.getPrintableWidth() / this.gridPane.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / this.gridPane.getBoundsInParent().getHeight();*/
        this.gridPane.getTransforms().add(new Scale(scaleX, scaleY));
       /*if(job != null){
            boolean succes = job.printPage(this.gridPane);
            if (succes) job.endJob();
        }*/
       if (job == null) return;
        boolean proceed = job.showPrintDialog(null);

        if (proceed) {
            boolean succes = job.printPage(pageLayout, this.gridPane);
            if (succes) job.endJob();
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
