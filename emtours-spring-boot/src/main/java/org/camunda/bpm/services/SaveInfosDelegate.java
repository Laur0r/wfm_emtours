package org.camunda.bpm.services;

import java.util.Date;
import java.util.List;

import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.emtours.CustomerRequestRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.entities.Customer;
import org.camunda.bpm.entities.CustomerRequest;
import org.camunda.bpm.sendMessages.Funspark.ActivityDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SaveInfosDelegate implements JavaDelegate {
	
	@Autowired(required = true)
	public CustomerRepository custrepository;
	@Autowired(required = true)
	public CustomerRequestRepository requestrepository;

	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
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

		//	validate customer already exist via email


		Customer cust = new Customer();
		cust.setName(name);
		cust.setGender(gender);
		cust.setAddress(address);
		cust.setZip (zip);
		cust.setCity (city);
		cust.setCountry (country);
		cust.setBirthday (birthday);
		cust.setEmail(email);
		cust = custrepository.save(cust);

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
