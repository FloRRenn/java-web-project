package cinema.ticket.booking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import cinema.ticket.booking.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Value("${app.default_sender}")
	private String default_sender;
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendMail(String toMail, String subject, String body) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(default_sender);
		mail.setTo(toMail);
		mail.setSubject(subject);
		mail.setText(body);
		
		mailSender.send(mail);
	}

}
