package com.eukolos.restapiwithtoutspring.configuration;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.SQLException;
@Slf4j
public class DataSourceConfig {
   public DataSource dataSource() throws SQLException {
       Server server = Server.createTcpServer("-tcpAllowOthers").start();
       log.info("H2 database server started and listening on port " + server.getPort());
       var dataSource = new DriverManagerDataSource(
               "jdbc:h2:~/test;INIT=\\;DROP ALL OBJECTS\\;RUNSCRIPT FROM 'classpath:create_schema.sql'\\;RUNSCRIPT FROM 'classpath:data.sql'",
               "sa", "");

       dataSource.setDriverClassName(Driver.class.getName());
       return dataSource;
   };
}
