package cinema.ticket.booking.response;

import cinema.ticket.booking.model.Payment;

public class PaymentResponse {
	
	private String id;
	private String email;
	private double price;
	private String createOn;
	private String status;
	private TicketDetail detail;
	private String paymentUrl;

	public PaymentResponse(Payment payment) {
		this.id = payment.getId();
		this.email = payment.getBooking().getUser().getEmail();
		this.price = payment.getAmount();
		this.createOn = payment.getCreateAt().toString();
		this.status = payment.getStatus().name();
		this.detail = new TicketDetail(payment.getBooking());
	}
	
	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

	public String getId() {
		return this.id;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public String getCreateOn() {
		return this.createOn;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public TicketDetail getDetai() {
		return this.detail;
	}

	public String getPaymentUrl() {
		return this.paymentUrl;
	}
}
