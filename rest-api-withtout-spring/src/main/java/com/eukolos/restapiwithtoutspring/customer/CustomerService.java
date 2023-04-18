package com.eukolos.restapiwithtoutspring.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final JdbcTemplate jdbcTemplate;

    // Create a customer
    public void createCustomer(Customer customer) {
        String sql = "INSERT INTO CUSTOMERS (id, name) VALUES (?, ?)";
        jdbcTemplate.update(sql, customer.id(), customer.name());
    }

    // Read a customer by ID
    public Customer getCustomerById(int id) {
        String sql = "SELECT id, name FROM CUSTOMERS WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id},
                (resultSet, rowNum) -> {
                    int customerId = resultSet.getInt("id");
                    String customerName = resultSet.getString("name");
                    return new Customer(customerId, customerName);
                });
    }

    // Update a customer
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE CUSTOMERS SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.name(), customer.id());
    }

    // Delete a customer by ID
    public void deleteCustomerById(int id) {
        String sql = "DELETE FROM CUSTOMERS WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        String sql = "SELECT id, name FROM CUSTOMERS";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    int customerId = resultSet.getInt("id");
                    String customerName = resultSet.getString("name");
                    return new Customer(customerId, customerName);
                });
    }

}
