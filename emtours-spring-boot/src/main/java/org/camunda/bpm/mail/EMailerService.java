package org.camunda.bpm.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EMailerService {

		private JavaMailSender emailSender;
		
	    public EMailerService (JavaMailSender emailSender) {
	    	this.emailSender = emailSender;
	    }
	    
	    public void sendSimpleMessage(
	      String to, String subject, String text) {
	        SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setTo(to); 
	        message.setSubject(subject); 
	        message.setText(text);
	        emailSender.send(message);
	    }
}
