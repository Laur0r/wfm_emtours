package org.camunda.bpm.services;

import java.util.Date;
import java.util.List;

import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.emtours.CustomerRequestRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.Customer;
import org.camunda.bpm.entities.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Saves the initial incoming customer information in the database
 */
@Component
public class SaveInfosDelegate implements JavaDelegate {
	
	@Autowired(required = true)
	public CustomerRepository custrepository;
	@Autowired(required = true)
	public CustomerRequestRepository requestrepository;

	public void execute(DelegateExecution execution) throws Exception {
		String name = (String) execution.getVariable("name");
		String gender = (String) execution.getVariable("gender");
		String address = (String) execution.getVariable("address");
		String zip = (String) execution.getVariable("zip");
		String city = (String) execution.getVariable("city");
		String country = (String) execution.getVariable("country");
		Date birthday = (Date) execution.getVariable("birthday");
		String email = (String) execution.getVariable("email");

		Date arrival = (Date) execution.getVariable("arrival");
		Date departure = (Date) execution.getVariable("departure");
		String climate = (String) execution.getVariable("climate");
		String experienceType = (String) execution.getVariable("experienceType");
		String budget = (String) execution.getVariable("budget");
		int numberPeople = 0;
		if(execution.getVariable("numberPeople") != null) {
			numberPeople = (Integer) execution.getVariable("numberPeople");
		}
		int numberActivities = 0;
		if(execution.getVariable("numberActivities") != null) {
			numberActivities = (Integer) execution.getVariable("numberActivities");
		}


		List<Customer> customers = custrepository.find(name, birthday);		
		Customer cust;
		
		// If the customer already exists in the database, they should not be added
		if(customers.isEmpty() == true){			
			cust = new Customer();
			cust.setName(name);
			cust.setGender(gender);
			cust.setAddress(address);
			cust.setZip (zip);
			cust.setCity (city);
			cust.setCountry (country);
			cust.setBirthday (birthday);
			cust.setEmail(email);
			cust = custrepository.save(cust);
		}
		else{
			cust = customers.get(0);
		}

		
		List<Customer> customers2 = custrepository.find(name, birthday);
		
		CustomerRequest custrequest = new CustomerRequest();
		custrequest.setArrival(arrival);
		custrequest.setDeparture(departure);
		custrequest.setClimate(climate);
		custrequest.setNumberPeople(numberPeople);
		custrequest.setExperienceType(experienceType);
		custrequest.setBudget(budget);
		custrequest.setNumberActivities(numberActivities);
		custrequest = requestrepository.save(custrequest);
		execution.setVariable("customerId", cust.getId());
		execution.setVariable("requestId", custrequest.getId());
	
	}

}
