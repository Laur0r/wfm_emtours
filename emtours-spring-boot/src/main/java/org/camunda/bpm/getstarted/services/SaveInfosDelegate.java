package org.camunda.bpm.getstarted.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.getstarted.emtours.Customer;
import org.camunda.bpm.getstarted.emtours.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveInfosDelegate implements JavaDelegate {
	
	@Autowired(required = true)
	public CustomerRepository repository; 

	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String name = (String) execution.getVariable("name");
		String email = (String) execution.getVariable("email");
		Customer cust = new Customer();
		cust.setName(name);
		cust.setEmail(email);
		repository.save(cust);
	}

}
