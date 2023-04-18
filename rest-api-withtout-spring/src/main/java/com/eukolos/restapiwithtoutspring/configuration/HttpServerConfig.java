package com.eukolos.restapiwithtoutspring.configuration;

import com.eukolos.restapiwithtoutspring.customer.CustomerController;
import com.sun.net.httpserver.HttpServer;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
@RequiredArgsConstructor
public class HttpServerConfig {
    private final HttpServer server;
    private final CustomerController customerController;
    public void getHttpServer() throws IOException {

        customerController.CustomerControllerMethod();
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
