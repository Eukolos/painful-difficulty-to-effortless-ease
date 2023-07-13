package com.eukolos.restapiwithspringframework.configuration;

import com.eukolos.restapiwithspringframework.customer.Customer;
import com.eukolos.restapiwithspringframework.customer.CustomerDao;
import com.eukolos.restapiwithspringframework.customer.CustomerService;
import com.eukolos.restapiwithspringframework.customer.CustomerServlet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.h2.Driver;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
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
    public CommandLineRunner commandLineRunner(CustomerDao customerDao){
        return args -> log.warn(
                customerDao.getAllCustomers().toString()
        );
    }

    @Bean
    public Gson getGson(){
        return new GsonBuilder().create();
    }
    @Bean
    public CustomerServlet customerServlet(CustomerService customerService, Gson gson){
        return new CustomerServlet(customerService, gson);
    }
    @Bean
    public CustomerService customerService(CustomerDao customerDao) {
        return new CustomerService(customerDao);
    }

    @Bean
    public CustomerDao customerDao(JdbcTemplate jdbcTemplate) {
        return new CustomerDao(jdbcTemplate);
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DriverManagerDataSource dataSource(Environment environment) throws SQLException {
        Server server = Server.createTcpServer("-tcpAllowOthers").start();
        log.info("H2 database server started and listening on port " + server.getPort());
        var dataSource = new DriverManagerDataSource(
                Objects.requireNonNull(server.getURL()),
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
