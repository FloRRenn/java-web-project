package cinema.ticket.booking.service;


public interface EmailService {
	void sendMail(String toMail, String subject, String body);
}
