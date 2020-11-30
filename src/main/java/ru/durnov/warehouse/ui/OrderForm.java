package ru.durnov.warehouse.ui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
        new ProductChooserPane(this).addEntityToEntityList();
    }

    void refresh() {
        this.getChildren().clear();
        this.rowCount = 0;
        this.show();
    }

    @Override
    public void show(){
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
            try {
                double productWeigth = Double.parseDouble(weigthTextField.getText().replace(',', '.'));
                double productCoast = Double.parseDouble(coastLabel.getText().replace(',', '.'));
                double sum = productWeigth*productCoast;
                String sumString = String.format("%.2f", sum);
                sumLabel.setText(sumString);
            } catch (NumberFormatException e) {
                NumberFormatExceptionWindow.show();
            }
        });
        this.add(weigthTextField, 2, rowCount);
        this.add(coastLabel, 3, rowCount);
        this.add(sumLabel, 4, rowCount);
        rowCount++;
    }



    public void addProduct(Product product, Double weigth){
        this.order.addProduct(product, weigth);
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

    public Order getOrder(){return this.order;}

    public List<Entity> getProductList() {
        return productList;
    }
}
