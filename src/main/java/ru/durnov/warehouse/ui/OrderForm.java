package ru.durnov.warehouse.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import ru.durnov.warehouse.daoservice.EntityDaoService;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.Store;
import ru.durnov.warehouse.print.ViewForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderForm extends AbstractPane {
    private final EntityDaoService productDao;
    private final List<Entity> productList;
    private final EntityDaoService orderDao;
    private final EntityDaoService storeDao;
    private Store store;
    private final Order order;
    private int rowCount;
    private boolean isSaved;
    private boolean dontSave;


    public OrderForm(EntityDaoService orderDao, EntityDaoService productDao, EntityDaoService storeDao) throws SQLException {
        super();
        super.setEntityButtonTitle("Добавить товар");
        this.productDao = productDao;
        this.orderDao = orderDao;
        this.storeDao = storeDao;
        if(storeDao.getAllEntity().size() < 1) setupFirstStore(storeDao);
        //this.store = (Store) storeDao.getAllEntity().get(0);
        this.productList = productDao.getAllEntity();
        selectStore(this);
        int number = getCurrentNumberForNewOrder();
        this.order = new Order(number + 1, store);
        this.rowCount = 0;
        this.isSaved = false;
    }

    private int getCurrentNumberForNewOrder() throws SQLException {
        int max = 0;
        List<Entity> entityList = this.orderDao.getAllEntity();
        for (Entity entity : entityList){
            Order order = (Order) entity;
            if (order.getId() > max) max = order.getId();
        }
        return max;
    }

    private void setupFirstStore(EntityDaoService storeDao) throws SQLException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Добавление первого магазина в базу");
        dialog.setHeaderText("Введите название магазина");
        dialog.setContentText("Магазин:");
        Optional<String> result = dialog.showAndWait();
        Store store;
        store = result.map(Store::new).orElseGet(() -> new Store(""));
        storeDao.addEntity(store);
    }

    private void selectStore(OrderForm orderForm) throws SQLException {
        StoreSetupForOrderPane storeSetupMaster = new StoreChoiceDialog(this);
        storeSetupMaster.setStoreForOrderPane();
    }

    @Override
    public void addEntityToEntityList() {
        new ProductChooserPane(this).addEntityToEntityList();
    }

    @Override
    public void refresh() {
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

    @Override
    public void removeEntityByTitle(Entity entity) throws SQLException {
        this.productDao.removeEntityByTitle(entity.getTitle());
        this.productList.remove(entity);
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
        Label label = new Label(String.valueOf(rowCount - 1));
        label.setPrefWidth(30);
        label.setAlignment(Pos.CENTER);
        this.add(label,0, rowCount);
        TextField purposeTextField = new TextField(product.getTitle());
        purposeTextField.setPrefWidth(500);
        purposeTextField.setEditable(false);
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
        weigthTextField.textProperty().addListener((observable,oldValue, newValue) -> {
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
        setupOrderProperties();
        for (Map.Entry<Product, Double> entry : order.getProductWeigthMap().entrySet()) {
            Product product = entry.getKey();
            Double weigth = entry.getValue();
            product.setWeight(weigth);
        }
        new ViewForm(order).show();
    }

    public void save() throws SQLException {
        setupOrderProperties();
        this.isSaved = true;
        orderDao.addEntity(order);
    }

    public void setupOrderProperties(){
        ObservableList<Node> children = this.getChildren();
        //Самое простое. Все предыдущие данные у накладной были с нулевым весом.
        //Это было необходимо для передачи продуктов из ProductChooserPane в orderForm
        //Теперь, после того, как мы заполнили веса, мы очищаем мапу и лист.
        this.order.getProductList().clear();
        this.order.getProductWeigthMap().clear();
        int productNumber = 0; //Переменная для сортировки продуктов в накладной
        for (int i = 6; i <children.size(); i = i + 5){
            int k = i + 1;
            TextField textField = (TextField) children.get(k);
            if (textField.getText().equals("")) break;
            Product product = new Product(textField.getText());
            product.setNumberInOrder(productNumber);
            k++;
            textField = (TextField) children.get(k);
            Double weigth = Double.valueOf(textField.getText().replace(',', '.'));
            k++;
            Label label = (Label) children.get(k);
            Double coast = Double.valueOf(label.getText().replace(',', '.'));
            product.setCoast(coast);
            order.addProduct(product, weigth);
            productNumber++;
        }
    }

    public List<Entity> getProductList() {
        return productList;
    }

    public List<Entity> getStoreList() throws SQLException {
        return this.storeDao.getAllEntity();
    }

    public void setStore(Store store){
        this.store = store;
    }

    public EntityDaoService getStoreDaoService(){
        return this.storeDao;
    }

    public boolean getIsSaved(){return this.isSaved;}

    public boolean getDontSave(){return dontSave;}

    public void showSavedDialog() throws SQLException {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Сохранение накладной");
        dialog.setHeaderText("Накладная не сохранена. Сохранить?");
        Button buttonOk = new Button("Сохранить");
        Button buttonCancel = new Button("Не сохранять");
        buttonOk.setOnAction(ae ->{
            dialog.setResult(true);
            dialog.close();
        });
        buttonCancel.setOnAction(ae -> {
            dialog.setResult(false);
            dialog.close();
        });
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().addAll(buttonOk, buttonCancel);
        flowPane.setHgap(30);
        dialog.getDialogPane().setContent(flowPane);
        Optional<Boolean> resultOptional = dialog.showAndWait();
        if (resultOptional.isPresent()){
            boolean result = resultOptional.get();
            if (result) {
                this.save();
            } else {
                dontSave = true;
            }
        } else {
            dontSave = true;
        }
    }
}
