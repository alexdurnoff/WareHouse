package ru.durnov.warehouse.print;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Scale;
import ru.durnov.warehouse.entity.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrintForm implements Cloneable {
    private final Order order;
    private GridPane gridPane;
    private TableView<ProductWrapper> tableView;
    private ObservableList<ProductWrapper> productWrappers;
    private String companyName;
    private double summ;


    public PrintForm(Order order) {
        this.companyName = getCompanyName();
        this.order = order;
        this.productWrappers = getProductWrapperList();
        calculateSumm();
        setupGridPaneAndTableView();
    }

    private void calculateSumm() {
        for (ProductWrapper productWrapper : productWrappers) {
            summ += productWrapper.getProduct().getCoast()*productWrapper.getProduct().getWeight();
        }
    }


    public String getCompanyName() {
        String name = null;
        try {
            List<String> stringList = Files.readAllLines(Paths.get("db/Наименование поставщика"));
            name = stringList.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    public void setCompanyName(String name){
        this.companyName = name;
        try (FileWriter fileWriter = new FileWriter(Paths.get("db/Наименование поставщика").toFile(), false)) {
            fileWriter.write(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupGridPaneAndTableView(){
        this.gridPane = new GridPane();
        this.tableView = new TableView<>();
        setupTableView();
        setupGridPane();
    }

    public PrintForm(Order order, List<Product> productList){
        this.order = new Order(order.getId(), order.getStore(), order.getDate());
        this.gridPane = new GridPane();
        this.tableView = new TableView<>();
        for (Product product : productList){
            this.order.getProductList().add(product);
        }
        setupTableView();
        setupGridPane();
    }

    private void setupTableView() {
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
            int k = 0;
            productWeigthMap.forEach(((product, weigth) -> {
                if (productWrapper.getProduct().equals(product)){
                    productWrapper.setWeigth(product.getWeight());
                    productWrapper.setCoast(product.getCoast());
                }
            } ));
            productWrapperList.add(productWrapper);
        }
        return FXCollections.observableList(productWrapperList);
    }

    private void setupGridPane() {
        Label dateLabel = new Label(new DataConvertorForOrder(this.order.getDate()).convertDate());
        dateLabel.setAlignment(Pos.BASELINE_RIGHT);
        dateLabel.setPrefWidth(400);
        Label orderTitleLabel = new Label(this.order.getTitle());
        orderTitleLabel.setAlignment(Pos.BASELINE_CENTER);
        orderTitleLabel.setPrefWidth(400);
        orderTitleLabel.setFont(Font.font("Serif", FontWeight.BOLD, 16));
        Label toLeftLabel = new Label("Кому");
        toLeftLabel.setAlignment(Pos.BASELINE_LEFT);
        toLeftLabel.setPrefWidth(40);
        Label toRightLabel = new Label(this.order.getStore().toString());
        toRightLabel.setAlignment(Pos.BASELINE_LEFT);
        Label fromLeftLabel = new Label("От");
        fromLeftLabel.setAlignment(Pos.BASELINE_RIGHT);
        Label fromRightLabel = new Label(this.companyName);
        fromRightLabel.setAlignment(Pos.BASELINE_LEFT);
        Label producerLabel = new Label("Сдал");
        Label consumerLabel = new Label("Принял");
        Label summNameLabel = new Label("Итого");
        Label summValueLabel = new Label(String.format("%.2f", this.summ));
        this.gridPane.add(dateLabel, 0, 0, 3, 1);
        this.gridPane.add(orderTitleLabel, 0, 1,3,1);
        this.gridPane.add(toLeftLabel, 0, 2);
        this.gridPane.add(toRightLabel, 1, 2);
        this.gridPane.add(fromLeftLabel, 0, 3);
        this.gridPane.add(fromRightLabel, 1, 3);
        this.gridPane.add(this.tableView, 0, 4, 3, 1);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(producerLabel, consumerLabel, summNameLabel, summValueLabel);
        producerLabel.setPadding(new Insets(0,80,0,0));
        consumerLabel.setPadding(new Insets(0,140,0,0));
        summNameLabel.setPadding(new Insets(0,10,0,0));
        summValueLabel.setPadding(new Insets(0,40,0,0));
        this.gridPane.add(hBox,0,5, 3, 1 );
        this.getGridPane().setVgap(10);
    }

    public void simplePrint() throws CloneNotSupportedException {
        javafx.print.PrinterJob job = PrinterJob.createPrinterJob();
        JobSettings jobSettings = job.getJobSettings();
        PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, 10, 50, 10, 10);
        jobSettings.setPageLayout(pageLayout);
        PrintForm printForm1 = (PrintForm) this.clone();
        GridPane printPane = new GridPane();
        printPane.getTransforms().add(new Scale(0.9,0.9));
        addPrintFormsToGridPane(printPane, printForm1);
        boolean printed = job.printPage(pageLayout, printPane);
        if (printed){
            job.endJob();
        }
    }

    public void addPrintFormsToGridPane(GridPane printPane, PrintForm printForm1){
        printPane.add(this.getGridPane(), 0, 0);
        printPane.add(printForm1.getGridPane(), 2, 0);
        printPane.setHgap(20);
    }

    public void print() throws CloneNotSupportedException {
        if (this.order.getProductList().size() < 15){
            simplePrint();
        } else {
            List<Product> lastProductList = this.order.getProductList().subList(16, this.order.getProductList().size());
            List<Product> firstProductList = this.order.getProductList().subList(0,15);
            this.order.setProductList(firstProductList);
            this.productWrappers = getProductWrapperList();
            setupGridPaneAndTableView();
            simplePrint();
            this.order.setProductList(lastProductList);
            this.productWrappers = getProductWrapperList();
            setupGridPaneAndTableView();
            reiseProductWrappersId();
            PrintForm printForm1 = new PrintForm(this.order);
            printForm1.reiseProductWrappersId();
            printForm1.setupGridPaneAndTableView();
            GridPane prinPane = new GridPane();
            addPrintFormsToGridPane(prinPane, printForm1);
            //simplePrint();
            print();
        }
    }

    private void reiseProductWrappersId() {
        for (ProductWrapper productWrapper : productWrappers){
            productWrapper.reiseId(15);
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        PrintForm printForm = (PrintForm) super.clone();
        printForm.productWrappers = this.productWrappers;
        printForm.setupGridPaneAndTableView();
        return printForm;
    }

    public void setProductWrappers(ObservableList<ProductWrapper> productWrappers) {
        this.productWrappers = productWrappers;
    }
}
