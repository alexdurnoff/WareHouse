package ru.durnov.warehouse.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.durnov.warehouse.dao.EntityDao;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;

import java.util.List;

public class OrderForm extends AbstractPane {
    private final List<Entity> productList;
    private final EntityDao orderDao;
    private final EntityDao storeDao;
    private Store store;
    private final Order order;
    private int rowCount;


    public OrderForm(EntityDao orderDao, EntityDao productDao, EntityDao storeDao){
        super();
        super.setEntityButtonTitle("Добавить товар");
        this.orderDao = orderDao;
        this.storeDao = storeDao;
        this.store = (Store) storeDao.getAllEntity().get(0);
        this.productList = productDao.getAllEntity();
        selectStore(this);
        int number = orderDao.getAllEntity().size();
        this.order = new Order(number, store);
        this.rowCount = 0;
    }

    private void selectStore(OrderForm orderForm) {
        new StoreChooserPane(this).setStoreForOrderpane();
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
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }

    private void addheaderLine() {
        Label label;
        label = new Label("Наименование магазина: " + this.store.getTitle() );
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(500);
        label.setPrefHeight(30);
        this.add(label, 1, 0);
        label = new Label("№ п.п.");
        label.setPrefWidth(70);
        label.setAlignment(Pos.CENTER);
        this.add(label,0, 1);
        label = new Label("Наименование");
        label.setPrefWidth(500);
        label.setAlignment(Pos.CENTER);
        this.add(label,1, 1);
        label = new Label("К-во");
        label.setPrefWidth(70);
        label.setAlignment(Pos.CENTER);
        this.add(label,2, 1);
        label = new Label("Цена");
        label.setPrefWidth(70);
        label.setAlignment(Pos.CENTER);
        this.add(label,3, 1);
        label = new Label("Сумма");
        label.setPrefWidth(70);
        label.setAlignment(Pos.CENTER);
        this.add(label,4, 1);
        rowCount = 2;
    }
    
    private void addProductLines(){
        this.order.getProductWeigthMap().forEach(this::addproductLine);
    }

    private void addproductLine(Product product, Double weigth) {
        Label label = new Label(String.valueOf(rowCount) + 1);
        label.setPrefWidth(30);
        label.setAlignment(Pos.CENTER);
        this.add(label,0, rowCount);
        TextField purposeTextField = new TextField(product.getTitle());
        purposeTextField.setPrefWidth(500);
        this.add(purposeTextField, 1, rowCount);
        TextField weigthTextField = new TextField(String.format("%.2f", weigth));
        weigthTextField.setPrefWidth(70);
        weigthTextField.setAlignment(Pos.CENTER);
        Label coastLabel = new Label(String.format("%.2f", product.getCoast()));
        coastLabel.setPrefWidth(70);
        coastLabel.setAlignment(Pos.CENTER);
        Label sumLabel = new Label(String.format("%.2f", product.getCoast()*weigth));
        sumLabel.setAlignment(Pos.CENTER);
        sumLabel.setPrefWidth(70);
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

    public List<Entity> getProductList() {
        return productList;
    }

    public List<Entity> getStoreList(){
        return this.storeDao.getAllEntity();
    }

    public void setStore(Store store){
        this.store = store;
    }
}
