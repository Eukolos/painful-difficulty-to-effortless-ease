package com.eukolos.restapiwithtoutspring.customer;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import lombok.RequiredArgsConstructor;

import java.io.OutputStream;

@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final HttpServer server;

    public void CustomerControllerMethod(){
        server.createContext("/api/customer", (exchange -> {
            //GET
            if ("GET".equals(exchange.getRequestMethod())) {

                String respText = customerService.getAllCustomers().toString();
                exchange.sendResponseHeaders(200, respText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(respText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
            //TODO POST

        }));
    }
}
