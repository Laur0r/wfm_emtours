package org.camunda.bpm.getstarted.services;

import java.util.Iterator;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.getstarted.emtours.Customer;
import org.camunda.bpm.getstarted.emtours.CustomerRepository;
import org.camunda.bpm.getstarted.emtours.LoggerDelegate;
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
