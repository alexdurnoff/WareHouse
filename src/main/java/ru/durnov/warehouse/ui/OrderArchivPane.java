package ru.durnov.warehouse.ui;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.durnov.warehouse.daoservice.EntityDaoService;
import ru.durnov.warehouse.entity.DateStringComparator;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.print.ViewForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderArchivPane extends AbstractPane {
    private List<Entity> orderList;
    private int rowCount;


    public OrderArchivPane(EntityDaoService orderDao){
        super();
        super.setAddEntityButton(null);
        this.entityDaoService = orderDao;
        this.message = "Вы уверены, что хотите удалить эту накладную?";
        this.removeEntityMessage = "Удалить накладную";
        this.rowCount = 0;
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
        OrderComparator orderComparator = new OrderComparator();
        this.orderList = entityDaoService.getAllEntity();
        this.orderList.sort(orderComparator);
        if (this.orderList.size() > 20) trimOrderListLast20Orders();
        List<String> dates = new ArrayList<>();
        makeDataList(dates);
        for (String date : dates) {
            ArrayList<Order> dataOrderList = new ArrayList<>();
            for (Entity entity : this.orderList) {
                Order order = (Order) entity;
                if (order.getDate().equals(date)) dataOrderList.add(order);
            }
            addDateLineToPane(date);
            fillThisPaneOrders(dataOrderList);
        }
        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }

    private void addDateLineToPane(String date) {
        Label label = new Label(date);
        label.setPrefWidth(300);
        label.setAlignment(Pos.CENTER);
        this.add(label, 1, rowCount);
        rowCount++;
    }

    private void makeDataList(List<String> dates) {
        for (Entity entity : this.orderList){
            Order order  = (Order) entity;
            String date = order.getDate();
            if (! dates.contains(date)) dates.add(date);
            dates.sort(new DateStringComparator());
        }
    }

    private void fillThisPaneOrders(List<Order> dataOrderList){
        for (int i = 0; i < dataOrderList.size(); i++){
            this.rowCount++;
            Order order = (Order) dataOrderList.get(i);
            Label label = new Label(String.valueOf(i + 1));
            label.setPrefWidth(30);
            label.setAlignment(Pos.CENTER);
            this.add(label,0, this.rowCount);
            this.add(new TextField(dataOrderList.get(i).getTitle()), 1, this.rowCount);
            this.add(new TextField(dataOrderList.get(i).getStore().getTitle()), 2, this.rowCount);
            this.add(new PrintButton(order), 3, this.rowCount);
            this.add(new RemoveEntityButton(order), 4, this.rowCount);
        }
        rowCount++;
    }

    private void trimOrderListLast20Orders() {
        int startNumber = this.orderList.size() - 20;
        int stopnumber = this.orderList.size();
        this.orderList.subList(startNumber, stopnumber);
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

    private static class OrderComparator implements Comparator<Entity>{

        @Override
        public int compare(Entity o1, Entity o2) {
            Order order1 = (Order) o1;
            Order order2 = (Order) o2;
            if (order1.getId() > order2.getId()) return 1;
            if (order1.getId() < order2.getId()) return -1;
            return 0;
        }

        @Override
        public Comparator<Entity> reversed() {
            return null;
        }
    }

}
