package ru.durnov.warehouse.ui;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import ru.durnov.warehouse.daoservice.CustomerDao;
import ru.durnov.warehouse.entity.Customer;
import ru.durnov.warehouse.entity.Entity;

import java.util.List;

public class CustomerPane extends AbstractPane {
    private List<Customer> customerList;
    private CustomerDao customerDao;

    public CustomerPane(CustomerDao customerDao){
        super();
        this.customerDao = customerDao;
        this.customerList = customerDao.getAllCustomers();
    }

    private void setColumns(){
        ObservableList<ColumnConstraints> columns = this.getColumnConstraints();
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHalignment(HPos.CENTER);
            columns.add(column);
        }
    }

    @Override
    public void show(){
        for (int i = 0; i <customerList.size(); i++){
            this.add(new Label(String.valueOf(i)),0, i);
            this.add(new TextField(customerList.get(i).getTitle()), 1, i);
        }
        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }

    @Override
    public void removeEntityByTitle(Entity entity) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void addEntityToEntityList() {

    }
}
