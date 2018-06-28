package org.camunda.bpm.sendMessages.Customer;

import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingConfirmationMessageDelegate implements JavaDelegate {

	@Autowired(required = true)
	public CustomerRepository custRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
	} 
}