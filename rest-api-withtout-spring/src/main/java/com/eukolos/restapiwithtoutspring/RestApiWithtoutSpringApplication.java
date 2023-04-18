package com.eukolos.restapiwithtoutspring;


import com.eukolos.restapiwithtoutspring.configuration.DataSourceConfig;
import com.eukolos.restapiwithtoutspring.configuration.HttpServerConfig;
import com.eukolos.restapiwithtoutspring.customer.CustomerController;
import com.eukolos.restapiwithtoutspring.customer.CustomerService;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;


@Slf4j
public class RestApiWithtoutSpringApplication {

	public static void main(String[] args) throws SQLException, IOException {

		var dataSource = new DataSourceConfig().dataSource();
		var cs = new CustomerService(dataSource);
		var server = HttpServer.create(new InetSocketAddress(8080), 0);
		var cc = new CustomerController(cs, server);
		new HttpServerConfig(server, cc).getHttpServer();


		var all = cs.getAllCustomers();
		all.forEach(c -> log.info(c.toString()));


	}
}
