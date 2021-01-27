package ru.durnov.warehouse.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.daoservice.EntityDaoService;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.entity.ProductComparator;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ProductPane extends AbstractPane {
    private List<Entity> productList;
    private GridPane gridPane;
    private ScrollPane scrollPane;

    public ProductPane(EntityDaoService productDao){
        super();
        super.setEntityButtonTitle("Добавить товар");
        this.entityDaoService = productDao;
        this.message = "Вы уверены, что хотите удалить этот продукт?";
        this.removeEntityMessage = "Удалить продукт";

    }

    @Override
    public void addEntityToEntityList() throws SQLException {
        constructNewProduct();
        this.productList = entityDaoService.getAllEntity();
    }

    private void constructNewProduct() {
        new ProductCreator(this).show();
    }

    @Override
    public void show() throws SQLException {
        this.gridPane = new GridPane();
        this.scrollPane = new ScrollPane(gridPane);
        this.scrollPane.setPadding(new Insets(5,5,5,5));
        //this.scrollPane.hbarPolicyProperty().set(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollPane.vbarPolicyProperty().set(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.add(scrollPane, 0, 0);
        this.productList = entityDaoService.getAllEntity();
        this.productList.sort(new ProductComparator());
        addHeader();
        for (int i = 0; i <productList.size(); i++){
            Product product = (Product) productList.get(i);
            Label label = new Label(String.valueOf(i + 1));
            label.setPrefWidth(30);
            label.setAlignment(Pos.CENTER);
            this.gridPane.add(label,0, i+1);
            TextField titleTextField = new TextField(productList.get(i).getTitle());
            titleTextField.setPrefWidth(270);
            this.gridPane.add(titleTextField, 1, i+1);
            TextField unitTextField = new TextField(String.valueOf(product.getUnit()));
            unitTextField.setPrefWidth(50);
            unitTextField.setAlignment(Pos.CENTER);
            this.gridPane.add(unitTextField, 2, i+1);
            TextField coastTextField = new TextField(String.valueOf(product.getCoast()));
            coastTextField.setPrefWidth(70);
            coastTextField.setAlignment(Pos.CENTER);
            this.gridPane.add(coastTextField, 3, i+1);
            this.gridPane.add(new RemoveEntityButton(product), 4, i+1);
            this.gridPane.add(new EditProductButton(product), 5, i+1);
        }
        this.gridPane.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }

    private void addHeader() {
        Label numberLabel = new Label("№ п.п");
        numberLabel.setAlignment(Pos.CENTER);
        numberLabel.setPrefHeight(40);
        numberLabel.setPrefWidth(40);
        Label productTitlelabel = new Label("Наименование");
        productTitlelabel.setPrefWidth(270);
        productTitlelabel.setAlignment(Pos.CENTER);
        Label unitLabel = new Label("кг/шт");
        unitLabel.setPrefWidth(50);
        unitLabel.setAlignment(Pos.CENTER);
        Label coastLabel = new Label("Цена");
        coastLabel.setPrefWidth(70);
        coastLabel.setAlignment(Pos.CENTER);
        this.gridPane.add(numberLabel, 0, 0);
        this.gridPane.add(productTitlelabel, 1, 0);
        this.gridPane.add(unitLabel, 2, 0);
        this.gridPane.add(coastLabel, 3, 0);
    }

    class EditProductButton extends Button{
        private Product product;

        EditProductButton(Product product){
            super("Редактировать");
            this.product = product;
            this.setOnAction(ae -> editProduct(this.product));
        }
    }

    private void editProduct(Product product) {
        new ProductEditorDialog(product).showAndWait();
        this.entityDaoService.updateProduct(product);
    }


}
