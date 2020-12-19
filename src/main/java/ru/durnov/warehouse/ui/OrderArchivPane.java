package ru.durnov.warehouse.ui;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ru.durnov.warehouse.daoservice.EntityDaoService;
import ru.durnov.warehouse.entity.DateStringComparator;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Order;
import ru.durnov.warehouse.entity.Product;
import ru.durnov.warehouse.print.ViewForm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
        addSearchLineToPane();
        OrderComparator orderComparator = new OrderComparator();
        this.orderList = entityDaoService.getAllEntity();
        this.orderList.sort(orderComparator);
        if (this.orderList.size() > 20) trimOrderListLast20Orders();
        addOrdersToPaneSortByDate();
        setupPaneParameters();
    }

    private void addSearchLineToPane() {
        HBox hBox = new HBox();
        Button searchByDateButton = new Button("Поиск по дате");
        TextField searchByDateTextField = new TextField();
        Button searchByNumberButton = new Button("Поиск по номеру");
        TextField searchByNumberTextField = new TextField();
        searchByDateButton.setOnAction(ae -> {
            try {
                this.searchByDate(searchByDateTextField.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        searchByNumberButton.setOnAction(ae -> {
            try {
                this.searchByNumber(searchByNumberTextField.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        hBox.getChildren().add(searchByDateTextField);
        hBox.getChildren().add(searchByDateButton);
        hBox.getChildren().add(searchByNumberTextField);
        hBox.getChildren().add(searchByNumberButton);
        this.add(hBox, 0, rowCount, 4, 1);
        rowCount++;
    }

    private void setupPaneParameters() {
        this.setAlignment(Pos.CENTER);
        this.setWidth(USE_PREF_SIZE);
    }

    private void addOrdersToPaneSortByDate() {
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

    public void searchByDate(String date) throws SQLException {
        this.getChildren().clear();
        addReturnAllOrderListButton();
        this.orderList = this.entityDaoService.getAllEntity();
        List<Order> removedOrders = new ArrayList<>();
        for (Entity entity : orderList) {
            Order order = (Order) entity;
            if(!(order.getDate().equals(date))) removedOrders.add(order);
        }
        this.orderList.removeAll(removedOrders);
        OrderComparator orderComparator = new OrderComparator();
        this.orderList.sort(orderComparator);
        addOrdersToPaneSortByDate();
        setupPaneParameters();
    }

    private void addReturnAllOrderListButton() {
        HBox hBox = new HBox();
        Button returnAllOrderListButton = new Button("Возврат к общему списку");
        returnAllOrderListButton.setOnAction(ae -> {
            try {
                this.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        hBox.getChildren().add(returnAllOrderListButton);
        this.add(hBox, 0, 0, 4, 1);
        rowCount++;
    }

    public void searchByNumber(String number) throws SQLException {
        this.getChildren().clear();
        addReturnAllOrderListButton();
        this.orderList = this.entityDaoService.getAllEntity();
        List<Order> removedOrders = new ArrayList<>();
        for (Entity entity : orderList) {
            Order order = (Order) entity;
            String numberString = String.valueOf(order.getId());
            if(!(numberString.equals(number)))removedOrders.add(order);
        }
        this.orderList.removeAll(removedOrders);
        OrderComparator orderComparator = new OrderComparator();
        this.orderList.sort(orderComparator);
        addOrdersToPaneSortByDate();
        setupPaneParameters();
    }



}
