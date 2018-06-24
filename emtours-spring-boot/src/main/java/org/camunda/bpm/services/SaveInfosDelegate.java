package org.camunda.bpm.services;

import java.util.Date;

import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.emtours.CustomerRequest;
import org.camunda.bpm.emtours.CustomerRequestRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveInfosDelegate implements JavaDelegate {
	
	@Autowired(required = true)
	public CustomerRepository custrepository;
	@Autowired(required = true)
	public CustomerRequestRepository requestrepository;

	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String name = (String) execution.getVariable("name");
		String address = (String) execution.getVariable("address");
		String zip = (String) execution.getVariable("zip");
		String city = (String) execution.getVariable("city");
		String country = (String) execution.getVariable("country");
		Date birthday = (Date) execution.getVariable("birthday");
		String email = (String) execution.getVariable("email");

		Date arrival = (Date) execution.getVariable("arrival");
		Date departure = (Date) execution.getVariable("departure");
		String climate = (String) execution.getVariable("climate");
		int numberPeople = (int) execution.getVariable("numberPeople");
		String experienceType = (String) execution.getVariable("experienceType");
		String budget = (String) execution.getVariable("budget");
		int numberActivities = (int) execution.getVariable("numberActivities");

		//	validate customer already exist via email


		Customer cust = new Customer();
		cust.setName(name);
		cust.setAddress(address);
//		cust.setZip (zip);
//		cust.setCity (city);
//		cust.setCountry (country);
		cust.setBirthday (birthday);
		cust.setEmail(email);
		custrepository.save(cust);

		CustomerRequest custrequest = new CustomerRequest();
//		custrequest.setEmail (email);
		custrequest.setArrival(arrival);
		custrequest.setDeparture(departure);
//		custrequest.setClimate(climate);
		custrequest.setNumberPeople(numberPeople);
		custrequest.setExperienceType(experienceType);
		custrequest.setBudget(budget);
		custrequest.setNumberActivities(numberActivities);
		requestrepository.save(custrequest);
		execution.setVariable("customerId", cust.getId());
		execution.setVariable("requestId", custrequest.getId());
	}

}
