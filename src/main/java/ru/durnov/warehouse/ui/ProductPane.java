package ru.durnov.warehouse.ui;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import ru.durnov.warehouse.dao.EntityDao;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.util.List;

public class ProductPane extends AbstractPane {
    private List<Entity> productList;
    private EntityDao productDao;

    public ProductPane(EntityDao productDao){
        super();
        super.setEntityButtonTitle("Добавить товар");
        this.productDao = productDao;
        this.productList = productDao.getAllEntity();
    }

    private void removeProductFromProductList(Product product) {
        this.productDao.removeEntityByTitle(product.getTitle());
        this.productList = productDao.getAllEntity();
    }

    @Override
    public void addEntityToEntityList() {
        constructNewProduct();
        this.productList = productDao.getAllEntity();
    }

    private void constructNewProduct() {
    }

    private void setColumns(){
        ObservableList<ColumnConstraints> columns = this.getColumnConstraints();
        for (int i = 0; i < 5; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHalignment(HPos.CENTER);
            columns.add(column);
        }
        this.getColumnConstraints().get(4).setFillWidth(true);
        this.getColumnConstraints().get(5).setFillWidth(true);
    }

    @Override
    public void show(){
        for (int i = 0; i <productList.size(); i++){
            Product product = (Product) productList.get(i);
            Label label = new Label(String.valueOf(i));
            label.setPrefWidth(30);
            label.setAlignment(Pos.CENTER);
            this.add(label,0, i);
            this.add(new TextField(productList.get(i).getTitle()), 1, i);
            this.add(new TextField(String.valueOf(product.getWeight())), 2, i);
            this.add(new TextField(String.valueOf(product.getCoast())), 3, i);
            this.add(new RemoveProductButton(product), 4, i);
            this.add(new EditProductButton(product), 5, i);
        }
        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }

    class RemoveProductButton extends Button {
        private Product product;

        RemoveProductButton(Product product){
            super("Удалить товар");
            this.product = product;
            this.setOnAction(ae -> removeProductFromProductList(this.product));
        }
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
    }

}
