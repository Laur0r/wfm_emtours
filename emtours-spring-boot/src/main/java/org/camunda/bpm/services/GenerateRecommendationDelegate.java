package org.camunda.bpm.services;

import java.util.Iterator;
import java.util.logging.Logger;

import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.emtours.LoggerDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateRecommendationDelegate implements JavaDelegate {

	 private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	 @Autowired
	 public CustomerRepository repository; 
	 
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		Iterable<Customer> cust = repository.findAll();
		Iterator<Customer> it = cust.iterator();
		while(it.hasNext()) {
			Customer c = it.next();
			LOGGER.info(c.getName());
		}
		LOGGER.info("!!!!!!!!!!!Process went through");

	}

}
