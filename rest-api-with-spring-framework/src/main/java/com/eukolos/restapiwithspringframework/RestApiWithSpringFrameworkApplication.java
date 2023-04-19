package com.eukolos.restapiwithspringframework;

import com.eukolos.restapiwithspringframework.configuration.BeanConfig;
import com.eukolos.restapiwithspringframework.customer.CustomerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class RestApiWithSpringFrameworkApplication {

	public static void main(String[] args) {
		var applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(BeanConfig.class);
		applicationContext.refresh();
		applicationContext.start();

		var cs = applicationContext.getBean(CustomerDao.class);
		log.info("cs.class={}", cs.getClass().getName());
		var all = cs.getAllCustomers();
		all.forEach(c -> log.info(c.toString()));

		// if we want to use this service on http request
		// we have to handle tomcat and servlet configuration
	}
}
