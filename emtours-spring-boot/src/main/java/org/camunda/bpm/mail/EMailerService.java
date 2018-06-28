package org.camunda.bpm.mail;

import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.camunda.bpm.emtours.CustomerRepository;
import org.camunda.bpm.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EMailerService {
	
	@Autowired(required = true)
	public CustomerRepository custRepository; 
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}

	private JavaMailSender emailSender;
	
    public EMailerService (JavaMailSender emailSender) {
    	this.emailSender = emailSender;
    }
    
    public void sendSimpleMessage(
      int customerId, String subject) {
    	
    	Optional<Customer> customer = custRepository.findById(customerId);
		
		String eMail = customer.get().getEmail();
    	if(isValidEmailAddress(eMail)) {
    		
    		String name = customer.get().getName();
    		String gender = customer.get().getGender();
    		
    		String salutation = "Ms.";
    		if(gender.equals("male")) {
				salutation = "Mr.";
			}
    		
    		String msg = String.format(INVALID_MSG, salutation, name);
    		
    		SimpleMailMessage message = new SimpleMailMessage(); 
            message.setTo(eMail); 
            message.setSubject(subject); 
            message.setText(msg);
            emailSender.send(message);
    	} else {
    		System.out.println("E-Mail not send: invalid Mail Adress!");
    	}
    }
    
    public static String INVALID_MSG = "Dear %s %s, " 
			  + "\nyour travel request can not be processed! "
			  + "\nThe data you have entered is invalid."
			  + "\n"
	          + "\nWe are looking forward to your next request!"
			  + "\n"
	          + "\nYours sincerely,"
	          + "\nemTours TravelAgency";
}
