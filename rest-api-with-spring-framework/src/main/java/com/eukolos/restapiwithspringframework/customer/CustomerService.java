package com.eukolos.restapiwithspringframework.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Component
public class CustomerService {
    private final CustomerDao dao;
    public Customer createCustomer(Customer customer) {
        return dao.createCustomer(customer);
    }

    // Read a customer by ID
    public Customer getCustomerById(int id) {
        return dao.getCustomerById(id);

    }

    // Update a customer
    public String updateCustomer(Customer customer) {
        dao.updateCustomer(customer);
        return "updated successfully";
    }

    // Delete a customer by ID
    public boolean deleteCustomerById(int id) {
        dao.deleteCustomerById(id);
        return true;
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return dao.getAllCustomers();
    }
}
