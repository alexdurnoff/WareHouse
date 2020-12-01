package ru.durnov.warehouse.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.durnov.warehouse.dao.EntityDao;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;

import java.util.List;

public class OrderArchivPane extends AbstractPane {
    private List<Entity> orderList;
    //private EntityDao orderDao;

    public OrderArchivPane(EntityDao orderDao){
        super();
        super.setAddEntityButton(null);
        this.entityDao = orderDao;
        this.orderList = orderDao.getAllEntity();
        this.message = "Вы уверены, что хотите удалить эту накладную?";
        this.removeEntityMessage = "Удалить накладную";
    }

    @Override
    public void addEntityToEntityList() {
        counstructNewOrder();
        this.orderList = entityDao.getAllEntity();
    }

    private void counstructNewOrder() {
    }



    @Override
    public void show() {
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

    /*@Override
    public void removeEntityByTitle(Entity entity) {
        this.entityDao.removeEntityByTitle(entity.getTitle());
        this.orderList.remove(entity);
    }

    @Override
    public void refresh() {
        this.getChildren().clear();
        this.show();
    }*/

    class PrintButton extends Button{
        private Order order;
        PrintButton(Order order){
            super("Печать");
            this.order = order;
            this.setOnAction(ae -> printOrder(order));
        }

    }

    private void printOrder(Order order) {
        System.out.println(order);
    }

    /*class RemoveEntityButton extends Button{
        private Order order;

        RemoveEntityButton(Order order){
            super("Удалить накладную");
            this.order = order;
            this.setOnAction(ae -> removeOrderFromOrderList(this.order));
        }
    }*/

    /*private void removeOrderFromOrderList(Order order) {
        new AYouSurePane(message, this, order).show();
        this.orderList = this.entityDao.getAllEntity();
    }*/
}
