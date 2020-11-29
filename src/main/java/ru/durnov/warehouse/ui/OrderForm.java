package ru.durnov.warehouse.ui;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import jdk.nashorn.internal.ir.CallNode;
import ru.durnov.warehouse.dao.EntityDao;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
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
        addEmptyLines();
        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }
    
    private void addEmptyLines(){
        for (int i = 1; i < 15; i++){
            Label label = new Label(String.valueOf(i));
            label.setPrefWidth(30);
            label.setAlignment(Pos.CENTER);
            this.add(label,0, i);
            this.add(new TextField(""), 1, i);
            this.add(new TextField(""), 2, i);
            this.add(new TextField(""), 3, i);
            this.add(new TextField(""), 4, i);
        }
    }
    
    private void addProductLines(){
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
    }
    


    public void print() {
    }

    public void save() {
        ObservableList<RowConstraints> rowConstraints = this.getRowConstraints();
        System.out.println(rowConstraints.size());
    }


}
