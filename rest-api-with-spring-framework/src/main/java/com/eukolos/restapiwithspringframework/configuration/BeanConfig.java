package com.eukolos.restapiwithspringframework.configuration;

import com.eukolos.restapiwithspringframework.customer.CustomerController;
import com.eukolos.restapiwithspringframework.customer.CustomerDao;
import com.eukolos.restapiwithspringframework.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/application.properties")
public class BeanConfig {
    @Value("classpath:create_schema.sql")
    private Resource createSchemaScript;

    @Value("classpath:data.sql")
    private Resource dataScript;
   /* @Bean
    TransactionTemplate transactionTemplate(PlatformTransactionManager ptm) {
        return new TransactionTemplate(ptm);
    }*/
    @Bean
    CustomerController customerController(CustomerService customerService){
        return new CustomerController(customerService);
    }
    @Bean
    CustomerService customerService(CustomerDao customerDao){
        return new CustomerService(customerDao);
    }
    @Bean
    CustomerDao customerDao(JdbcTemplate jdbcTemplate) {
        return new CustomerDao(jdbcTemplate);
    }
    @Bean
    DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    DriverManagerDataSource dataSource(Environment environment) throws SQLException {
        Server server = Server.createTcpServer("-tcpAllowOthers").start();
        log.info("H2 database server started and listening on port " + server.getPort());
        var dataSource = new DriverManagerDataSource(
                Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                Objects.requireNonNull(environment.getProperty("spring.datasource.username")),
                Objects.requireNonNull(environment.getProperty("spring.datasource.password")));

        dataSource.setDriverClassName(Driver.class.getName());
        return dataSource;
    }
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(createSchemaScript);
        populator.addScript(dataScript);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}
