package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.durnov.warehouse.daoservice.EntityDaoService;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.print.ViewForm;

import java.sql.SQLException;
import java.util.List;

public class OrderArchivPane extends AbstractPane {
    private List<Entity> orderList;

    public OrderArchivPane(EntityDaoService orderDao){
        super();
        super.setAddEntityButton(null);
        this.entityDaoService = orderDao;
        this.message = "Вы уверены, что хотите удалить эту накладную?";
        this.removeEntityMessage = "Удалить накладную";
    }

    @Override
    public void addEntityToEntityList() throws SQLException {
        counstructNewOrder();
        this.orderList = entityDaoService.getAllEntity();
    }

    private void counstructNewOrder() {
    }



    @Override
    public void show() throws SQLException {
        this.orderList = entityDaoService.getAllEntity();
        for (int i = 0; i < orderList.size(); i++){
            Order order = (Order) orderList.get(i);
            Label label = new Label(String.valueOf(i));
            label.setPrefWidth(30);
            label.setAlignment(Pos.CENTER);
            this.add(label,0, i);
            this.add(new TextField(orderList.get(i).getTitle()), 1, i);
            this.add(new PrintButton(order), 2, i);
            this.add(new RemoveEntityButton(order), 3, i);
        }
        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }


    class PrintButton extends Button{
        private Order order;
        PrintButton(Order order){
            super("Печать");
            this.order = order;
            this.setOnAction(ae -> printOrder(order));
        }

    }

    private void printOrder(Order order) {
       new ViewForm(order).show();
    }

}
