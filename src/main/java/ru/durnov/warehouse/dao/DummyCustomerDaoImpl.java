package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Customer;
import ru.durnov.warehouse.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class DummyCustomerDaoImpl implements EntityDao {
    private List<Entity> customerList;

    public DummyCustomerDaoImpl(){
        this.customerList = new ArrayList<>();
        this.customerList.add(new Customer("Рога и копыта"));
        this.customerList.add(new Customer("Авангард"));
        this.customerList.add(new Customer("Пятерочка"));
        this.customerList.add(new Customer("Шестерочка"));
        this.customerList.add(new Customer("Перекресток"));
    }


    @Override
    public List<Entity> getAllEntity() {
        return customerList;
    }

    @Override
    public Entity getEntityByTitle(String title) {
        for (Entity customer : customerList){
            if (customer.getTitle().equals(title)) return customer;
        }
        return null;
    }

    @Override
    public Customer getEntityById(int id) {
        return null;
    }

    @Override
    public void addEntity(Entity customer) {
        customerList.add((Customer) customer);
    }

    @Override
    public void removeEntityByTitle(String title) {
        for (Entity customer : customerList){
            if (customer.getTitle().equals(title)) customerList.remove(customer);
        }
    }

    @Override
    public void removeEntityByid(int id) {

    }
}
