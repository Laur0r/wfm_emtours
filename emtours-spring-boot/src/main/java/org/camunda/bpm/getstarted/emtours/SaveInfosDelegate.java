package org.camunda.bpm.getstarted.emtours;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveInfosDelegate implements JavaDelegate {
	
	@Autowired(required = true)
	public CustomerRepository repository; 

	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		Object name = execution.getVariable("name");
		Object email = execution.getVariable("email");
		Customer cust = new Customer();
		cust.setName((String)name);
		cust.setEmail((String)email);
		repository.save(cust);
	}

}
