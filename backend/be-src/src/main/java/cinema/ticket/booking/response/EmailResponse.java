package cinema.ticket.booking.response;

public class EmailResponse {
	private String mail;
	private String subject;
	private String content;
	
	public EmailResponse(String mail, String subject, String content) {
		this.mail = mail;
		this.subject = subject;
		this.content = content;
	}
	
	public String getMail() {
		return this.mail;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public String getContent() {
		return this.content;
	}
}
