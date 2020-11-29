package ru.durnov.warehouse.dao;

import ru.durnov.warehouse.entity.Customer;

import java.util.List;

public interface CustomerDao {
    public List<Customer> getAllCustomers();
    public Customer getCustomerByTitle(String title);
    public Customer getCustomerbyId(int id);
    public void addCustomer(Customer customer);
    public void removeCustomerByTitle(String title);
    public void removeCustomerById(int id);
}
