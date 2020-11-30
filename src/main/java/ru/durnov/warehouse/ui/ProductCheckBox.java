package ru.durnov.warehouse.ui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import ru.durnov.warehouse.entity.Product;

public class ProductCheckBox extends CheckBox {
    private Product product;

    public ProductCheckBox(Product product){
        this.product = product;
        this.setText(product.getTitle());
    }

    public Product getProduct(){
        return product;
    }
}
