package ru.durnov.warehouse.ui;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.print.PrintForm;

public class ProductEditorDialog extends Dialog<Boolean> {
    private Product product;

    public ProductEditorDialog(Product product){
        this.product = product;
        Label titleLabel = new Label(product.getTitle());
        TextField coastthTextField = new TextField(String.valueOf(product.getWeight()));
        coastthTextField.textProperty().addListener((observable,oldValue, newValue) ->{
            try {
                double coast = Double.parseDouble(coastthTextField.getText().replace(',', '.'));
                product.setCoast(coast);
            } catch (NumberFormatException exception){
                NumberFormatExceptionWindow.show();
            }
        });
        TextField unitTextField = new TextField(product.getUnit());
        unitTextField.setOnAction(ae -> product.setUnit(unitTextField.getText()));
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Наименование"), 0, 0);
        gridPane.add(new Label("Стоимость"), 1, 0);
        gridPane.add(new Label("кг/шт"), 2, 0);
        gridPane.add(titleLabel, 0, 1);
        gridPane.add(coastthTextField, 1, 1);
        gridPane.add(unitTextField, 2, 1);
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        this.getDialogPane().setContent(gridPane);
        this.getDialogPane().getButtonTypes().add(buttonTypeOk);
    }
}
