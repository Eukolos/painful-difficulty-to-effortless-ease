package com.eukolos.restapiwithtoutspring.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class CustomerService {

    private final DataSource dataSource;

    public CustomerService( DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Collection<Customer> all() {
        ArrayList<Customer> listOfCustomers = new ArrayList<Customer>();
        try {
            try (var connection = this.dataSource.getConnection()) {
                try (var stmt = connection.createStatement()) {
                    try (var resultSet = stmt.executeQuery("select * from customers")) {
                        while (resultSet.next()) {
                            listOfCustomers.add(new Customer(resultSet.getInt("id"), resultSet.getString("name")));
                        }
                    }
                }
            }
        } //
        catch (Exception th) {
            log.error("something went terribly wrong, but search me, I have no idea what!",
                    th);
        }
        return listOfCustomers;
    }

}
