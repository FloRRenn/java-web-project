package cinema.ticket.booking.service;

import java.util.List;

import cinema.ticket.booking.model.Payment;
import cinema.ticket.booking.request.HashRequest;
import cinema.ticket.booking.request.PaymentRequest;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.response.PaymentResponse;

public interface PaymentService {
	
	public PaymentResponse create(String username, PaymentRequest request, String ip_addr);
	public PaymentResponse getFromId(String username, String payment_id);
	public List<PaymentResponse> getAllPaymentsOfUser(String username);
	public boolean checkPaymentInfo(PaymentRequest request);
	public MyApiResponse verifyPayment(String username, String payment_id);
	
	public String createHash(HashRequest rawdata);
	public void addPaymentMail(Payment payment);

}
