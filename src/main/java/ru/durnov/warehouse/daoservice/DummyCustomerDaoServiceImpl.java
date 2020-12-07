package ru.durnov.warehouse.daoservice;

import ru.durnov.warehouse.entity.Customer;
import ru.durnov.warehouse.entity.Entity;
import ru.durnov.warehouse.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DummyCustomerDaoServiceImpl implements EntityDaoService {
    private List<Entity> customerList;

    public DummyCustomerDaoServiceImpl(){
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
    public void updateProduct(Product product) {

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
