package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.durnov.warehouse.dao.EntityDao;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.util.List;

public class ProductPane extends AbstractPane {
    private List<Entity> productList;

    public ProductPane(EntityDao productDao){
        super();
        super.setEntityButtonTitle("Добавить товар");
        this.entityDao = productDao;
        this.productList = productDao.getAllEntity();
        this.message = "Вы уверены, что хотите удалить этот продукт?";
        this.removeEntityMessage = "Удалить продукт";
    }

    @Override
    public void addEntityToEntityList() {
        constructNewProduct();
        this.productList = entityDao.getAllEntity();
    }

    private void constructNewProduct() {
        new ProductCreator(this).show();
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
            this.add(new RemoveEntityButton(product), 4, i);
            this.add(new EditProductButton(product), 5, i);
        }
        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
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
