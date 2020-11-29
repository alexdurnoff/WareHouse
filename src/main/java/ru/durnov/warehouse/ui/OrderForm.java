package ru.durnov.warehouse.ui;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import ru.durnov.warehouse.dao.EntityDao;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderForm extends AbstractPane {
    private List<Entity> productList;
    private EntityDao productDao;

    public OrderForm(){
        super();
        super.setEntityButtonTitle("Добавить товар");
        this.productList = new ArrayList<>();
    }

    @Override
    public void addEntityToEntityList() {

    }

    private void setColumns(){
        ObservableList<ColumnConstraints> columns = this.getColumnConstraints();
        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHalignment(HPos.CENTER);
            columns.add(column);
        }
    }

    public void show(){
        for (int i = 0; i <productList.size(); i++){
            Product product = (Product) productList.get(i);
            double weight = product.getWeight();
            double coast = product.getCoast();
            Label label = new Label(String.valueOf(i));
            label.setPrefWidth(30);
            label.setAlignment(Pos.CENTER);
            this.add(label,0, i);
            this.add(new TextField(productList.get(i).getTitle()), 1, i);
            this.add(new TextField(String.valueOf(weight)), 2, i);
            this.add(new TextField(String.valueOf(coast)), 3, i);
            this.add(new TextField(String.valueOf(weight * coast)), 4, i);
        }
        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }


}
