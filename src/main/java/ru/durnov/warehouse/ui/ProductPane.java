package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.durnov.warehouse.daoservice.EntityDaoService;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductPane extends AbstractPane {
    private List<Entity> productList;

    public ProductPane(EntityDaoService productDao){
        super();
        super.setEntityButtonTitle("Добавить товар");
        this.entityDaoService = productDao;
        //this.productList = productDao.getAllEntity();
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
        this.productList = entityDaoService.getAllEntity();
        addHeader();
        for (int i = 0; i <productList.size(); i++){
            Product product = (Product) productList.get(i);
            Label label = new Label(String.valueOf(i + 1));
            label.setPrefWidth(30);
            label.setAlignment(Pos.CENTER);
            this.add(label,0, i+1);
            TextField titleTextField = new TextField(productList.get(i).getTitle());
            titleTextField.setPrefWidth(200);
            this.add(titleTextField, 1, i+1);
            TextField weightTextField = new TextField(String.valueOf(product.getWeight()));
            weightTextField.setPrefWidth(70);
            weightTextField.setAlignment(Pos.CENTER);
            this.add(weightTextField, 2, i+1);
            TextField coastTextField = new TextField(String.valueOf(product.getCoast()));
            coastTextField.setPrefWidth(70);
            coastTextField.setAlignment(Pos.CENTER);
            this.add(coastTextField, 3, i+1);
            this.add(new RemoveEntityButton(product), 4, i+1);
            this.add(new EditProductButton(product), 5, i+1);
        }
        //this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }

    private void addHeader() {
        Label numberLabel = new Label("№ п.п");
        numberLabel.setAlignment(Pos.CENTER);
        numberLabel.setPrefHeight(40);
        numberLabel.setPrefWidth(40);
        Label productTitlelabel = new Label("Наименование");
        productTitlelabel.setPrefWidth(200);
        productTitlelabel.setAlignment(Pos.CENTER);
        Label weigthLabel = new Label("Вес");
        weigthLabel.setPrefWidth(70);
        weigthLabel.setAlignment(Pos.CENTER);
        Label coastLabel = new Label("Цена");
        coastLabel.setPrefWidth(70);
        coastLabel.setAlignment(Pos.CENTER);
        this.add(numberLabel, 0, 0);
        this.add(productTitlelabel, 1, 0);
        this.add(weigthLabel, 2, 0);
        this.add(coastLabel, 3, 0);
    }

    class EditProductButton extends Button{
        private Product product;

        EditProductButton(Product product){
            super("Редактировать товар");
            this.product = product;
            this.setOnAction(ae -> editProduct(this.product));
        }
    }

    private void editProduct(Product product) {
        new ProductEditor(this, product).show();
    }

}
