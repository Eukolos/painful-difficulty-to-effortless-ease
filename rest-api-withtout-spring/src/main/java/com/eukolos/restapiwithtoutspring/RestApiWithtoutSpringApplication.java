package com.eukolos.restapiwithtoutspring;


import com.eukolos.restapiwithtoutspring.configuration.DataSourceConfig;
import com.eukolos.restapiwithtoutspring.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.SQLException;


@Slf4j
public class RestApiWithtoutSpringApplication {

	public static void main(String[] args) throws SQLException {

		var dataSource = new DataSourceConfig().dataSource();
		var template = new JdbcTemplate(dataSource);// not used
		template.afterPropertiesSet();
		var cs = new CustomerService(template);
		var all = cs.getAllCustomers();
		all.forEach(c -> log.info(c.toString()));

	}
}
