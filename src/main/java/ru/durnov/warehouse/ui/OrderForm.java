package ru.durnov.warehouse.ui;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.durnov.warehouse.dao.EntityDao;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderForm extends AbstractPane {
    private List<Entity> productList;
    private EntityDao orderDao;
    private Order order;
    private int number;
    private int rowCount;


    public OrderForm(EntityDao orderDao, EntityDao productDao){
        super();
        super.setEntityButtonTitle("Добавить товар");
        this.orderDao = orderDao;
        this.productList = productDao.getAllEntity();
        this.number = orderDao.getAllEntity().size();
        this.order = new Order(number);
        this.rowCount = 0;
    }

    @Override
    public void addEntityToEntityList() {
        GridPane rootNode = new GridPane();
        GridPane productChooser = new GridPane();
        productChooser.setAlignment(Pos.CENTER);
        productChooser.setPrefWidth(400);
        productChooser.setGridLinesVisible(true);
        ScrollPane scrollPane = new ScrollPane(productChooser);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);
        Scene scene = new Scene(rootNode, 450, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        Set<Product> choosedProductSet = new HashSet<>();
        Button buttonOk = new Button("Ok");
        buttonOk.setOnAction(ae ->{
            choosedProductSet.forEach(p -> this.order.addProduct(p, 0.));
            stage.close();
            this.refresh();
        });
        rootNode.add(buttonOk,0,0);
        rootNode.add(scrollPane, 0, 1);
        addHeaderProductChooserLine(productChooser);
        for (int i = 1; i < productList.size(); i++){
            Product product = (Product) productList.get(i);
            Label label = new Label(product.getTitle());
            productChooser.add(label, 0, i +1);
            label = new Label(String.format("%.2f", product.getCoast()));
            productChooser.add(label, 1, i + 1);
            ProductCheckBox productCheckBox = new ProductCheckBox(product);
            productCheckBox.setOnAction(ae -> choosedProductSet.add(productCheckBox.getProduct()));
            productChooser.add(productCheckBox, 2, i +1);
        }
        stage.show();
    }

    private void addHeaderProductChooserLine(GridPane productChooser) {
        Label label = new Label("Наименование товара");
        productChooser.add(label, 0, 0);
        label = new Label("Стоимость товара");
        productChooser.add(label, 1, 0);
        label = new Label("Выбрать товар");
        productChooser.add(label, 2, 0);
    }

    private void refresh() {
        this.getChildren().clear();
        this.rowCount = 0;
        this.show();
    }

    private void setColumns(){
        ObservableList<ColumnConstraints> columns = this.getColumnConstraints();
        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHalignment(HPos.CENTER);
            columns.add(column);
        }
    }

    @Override
    public void show(){
        //addEmptyLines();
        addheaderLine();
        addProductLines();
        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }

    private void addheaderLine() {
        Label label = new Label("№ п.п.");
        label.setPrefWidth(70);
        label.setAlignment(Pos.CENTER);
        this.add(label,0, 0);
        label = new Label("Наименование");
        label.setPrefWidth(500);
        label.setAlignment(Pos.CENTER);
        this.add(label,1, 0);
        label = new Label("К-во");
        label.setPrefWidth(70);
        label.setAlignment(Pos.CENTER);
        this.add(label,2, 0);
        label = new Label("Цена");
        label.setPrefWidth(70);
        label.setAlignment(Pos.CENTER);
        this.add(label,3, 0);
        label = new Label("Сумма");
        label.setPrefWidth(70);
        label.setAlignment(Pos.CENTER);
        this.add(label,4, 0);
        rowCount = 1;
    }

    private void addEmptyLines(){
        for (int i = 1; i < 15; i++){
            Label label = new Label(String.valueOf(i));
            label.setPrefWidth(30);
            label.setAlignment(Pos.CENTER);
            this.add(label,0, i);
            this.add(new TextField(""), 1, i);
            this.add(new TextField(""), 2, i);
            this.add(new TextField(""), 3, i);
            this.add(new TextField(""), 4, i);
        }
    }
    
    private void addProductLines(){
        this.order.getProductWeigthMap().forEach(this::addproductLine);
    }

    private void addproductLine(Product product, Double weigth) {
        Label label = new Label(String.valueOf(rowCount) + 1);
        label.setPrefWidth(30);
        label.setAlignment(Pos.CENTER);
        this.add(label,0, rowCount);
        this.add(new TextField(product.getTitle()), 1, rowCount);
        TextField weigthTextField = new TextField(String.format("%.2f", weigth));
        Label coastLabel = new Label(String.format("%.2f", product.getCoast()));
        Label sumLabel = new Label(String.format("%.2f", product.getCoast()*weigth));
        weigthTextField.setOnAction(ae ->{
            double productWeigth = Double.parseDouble(weigthTextField.getText().replace(',', '.'));
            double productCoast = Double.parseDouble(coastLabel.getText().replace(',', '.'));
            double sum = productWeigth*productCoast;
            String sumString = String.format("%.2f", sum);
            sumLabel.setText(sumString);
        });
        this.add(weigthTextField, 2, rowCount);
        this.add(coastLabel, 3, rowCount);
        this.add(sumLabel, 4, rowCount);
        rowCount++;
    }




    public void print() {
    }

    public void save() {
        ObservableList<Node> children = this.getChildren();
        for (int i = 5; i <children.size(); i = i + 5){
            Label label = (Label) children.get(i);
            int number = Integer.parseInt(label.getText());
            Order order = new Order(number);
            int k = i + 1;
            TextField textField = (TextField) children.get(k);
            if (textField.getText().equals("")) break;
            Product product = new Product(textField.getText());
            k++;
            textField = (TextField) children.get(k);
            Double weigth = Double.valueOf(textField.getText().replace(',', '.'));
            order.addProduct(product, weigth);
        }
        orderDao.addEntity(order);
    }


}
