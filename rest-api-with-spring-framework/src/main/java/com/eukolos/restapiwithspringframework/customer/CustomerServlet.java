package com.eukolos.restapiwithspringframework.customer;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RequiredArgsConstructor
public class CustomerServlet extends HttpServlet {
    private final CustomerService service;
    private final Gson gson;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Customer> customerList = service.getAllCustomers();
        String json = gson.toJson(customerList);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
    // i have to create new servlet for new path and new doGet
   /* @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Get customer ID from request parameters
        Integer customerId = Integer.valueOf(req.getParameter("id"));

        // If the ID is provided, fetch a specific customer by ID
        if (customerId != null) {
            Customer customer = service.getCustomerById(customerId);
            if (customer != null) {
                String json = gson.toJson(customer);
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                out.print(json);
                out.flush();
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            // Fetch all customers
            List<Customer> customerList = service.getAllCustomers();
            String json = gson.toJson(customerList);
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        }
    }*/

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Read the JSON payload from the request body
        BufferedReader reader = req.getReader();
        Customer customer = gson.fromJson(reader, Customer.class);

        // Create the customer
        service.createCustomer(customer);

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Read the JSON payload from the request body
        BufferedReader reader = req.getReader();
        Customer customer = gson.fromJson(reader, Customer.class);

        // Update the customer
        service.updateCustomer(customer);

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Get customer ID from request parameters
        String customerId = req.getParameter("id");

        // Delete the customer
        boolean deleted = service.deleteCustomerById(Integer.parseInt(customerId));

        if (deleted) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
