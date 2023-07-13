package com.eukolos.restapiwithspringframework.configuration;

import com.eukolos.restapiwithspringframework.customer.CustomerService;
import com.eukolos.restapiwithspringframework.customer.CustomerServlet;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TomcatConfig {
    private final Gson gson;
    private final CustomerService service;

    @PostConstruct
    public void getTomcat() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        Context context = tomcat.addContext("/", null);
        Tomcat.addServlet(context, "customerServlet", new CustomerServlet(service, gson));
        context.addServletMappingDecoded("/","customerServlet");

        tomcat.start();

        new Thread(() -> tomcat.getServer().await()).start();
    }
}
