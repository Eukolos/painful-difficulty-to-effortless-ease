package com.eukolos.restapiwithtoutspring.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final DataSource dataSource;
    private final TransactionTemplate tt;

    // Create a customer
    public void createCustomer(Customer customer) {
        String sql = "INSERT INTO CUSTOMERS (id, name) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, customer.id());
            statement.setString(2, customer.name());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Read a customer by ID
    public Customer getCustomerById(int id) {
        return this.tt.execute(status -> {
            String sql = "SELECT id, name FROM CUSTOMERS WHERE id = ?";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int customerId = resultSet.getInt("id");
                        String customerName = resultSet.getString("name");
                        return new Customer(customerId, customerName);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

    }


    // Update a customer
    public void updateCustomer(Customer customer) {
        this.tt.execute(status -> {
            String sql = "UPDATE CUSTOMERS SET name = ? WHERE id = ?";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, customer.name());
                statement.setInt(2, customer.id());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

    }


    // Delete a customer by ID
    public void deleteCustomerById(int id) {

        this.tt.execute(status -> {
            String sql = "DELETE FROM CUSTOMERS WHERE id = ?";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });

    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return this.tt.execute(status -> {
            String sql = "SELECT id, name FROM CUSTOMERS";
            List<Customer> customers = new ArrayList<>();
            try (Connection conn = dataSource.getConnection();
                 Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    int customerId = resultSet.getInt("id");
                    String customerName = resultSet.getString("name");
                    Customer customer = new Customer(customerId, customerName);
                    customers.add(customer);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return customers;
        });
    }

}
