package cinema.ticket.booking.service.impl;

import java.util.List;
import java.util.Queue;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cinema.ticket.booking.exception.MyBadRequestException;
import cinema.ticket.booking.exception.MyNotFoundException;
import cinema.ticket.booking.model.Account;
import cinema.ticket.booking.model.Booking;
import cinema.ticket.booking.model.Payment;
import cinema.ticket.booking.model.enumModel.BookingStatus;
import cinema.ticket.booking.model.enumModel.PaymentStatus;
import cinema.ticket.booking.repository.BookingRepository;
import cinema.ticket.booking.repository.PaymentRepository;
import cinema.ticket.booking.repository.UserRepository;
import cinema.ticket.booking.request.HashRequest;
import cinema.ticket.booking.request.PaymentRequest;
import cinema.ticket.booking.response.PaymentResponse;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.service.EmailService;
import cinema.ticket.booking.service.PaymentService;
import cinema.ticket.booking.utils.HashUtil;
import cinema.ticket.booking.utils.VNPay;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	final private int SEND_MAIL_SCHEDULE = 1000;
	
	Queue<PaymentResponse> sendEmail = new LinkedList<>();
	
	@Autowired
	private UserRepository userREPO;
	
	@Autowired
	private BookingRepository bookingREPO;
	
	
	@Autowired
	private PaymentRepository paymentREPO;
	
	@Autowired
	private EmailService emailSER;
	
	@Override
	public PaymentResponse create(String username, PaymentRequest request, String ip_addr) {
		Booking booking = bookingREPO.findById(request.getBookingId()).orElseThrow(() -> new MyNotFoundException("Ticket ID " + request.getBookingId() + " is not found"));
		if (!booking.getStatus().equals(BookingStatus.PENDING))
			throw new MyBadRequestException("This ticket have been already paid or canceled before.");
		
		List<Payment> payments = paymentREPO.findAllByBookingId(booking.getId());
		if (payments.size() != 0) 
			throw new MyBadRequestException("This ticket have been already pending for payment.");

		String userFromBooking = booking.getUser().getUsername();
		if (!username.equals(userFromBooking))
			throw new MyNotFoundException("Ticket ID " + request.getBookingId() + " is not found");
		
		double price = booking.getPriceFromListSeats();

		Payment payment = new Payment(booking, price);
		Payment save = paymentREPO.save(payment);
		
		String res = "none";
		try {
			res = VNPay.createPay(payment, request.getPaymentType(), ip_addr);
		} catch (Exception e) {
			payment.setStatus(PaymentStatus.CANCLED);
			System.out.println(e.toString());
		}

		save = paymentREPO.save(save);
		PaymentResponse resp = new PaymentResponse(save);
		resp.setPaymentUrl(res);
		return resp;
	}

	@Override
	public PaymentResponse getFromId(String username, String payment_id) {
		Payment payment = paymentREPO.findById(payment_id).orElseThrow(() -> new MyNotFoundException("Payment ID not found"));
		String userOfpayment = payment.getBooking().getUser().getUsername();
		if (username.equals(userOfpayment))
			return new PaymentResponse(payment);
		throw new MyNotFoundException("Payment ID not found");
	}
	
	@Override
	public MyApiResponse verifyPayment(String username, String payment_id) {
		Payment payment = paymentREPO.findById(payment_id).orElseThrow(() -> new MyNotFoundException("Payment ID not found"));
		String userOfpayment = payment.getBooking().getUser().getUsername();
		if (username.equals(userOfpayment)) {
			if (payment.getStatus() != PaymentStatus.PENDING)
				return new MyApiResponse("This ticket have been already paid or canceled before.");

			try {
				Integer paid = VNPay.verifyPay(payment);

				if (paid == 0) {
					payment.setStatus(PaymentStatus.PAID);
					paymentREPO.save(payment);
					return new MyApiResponse("Ticket is paid. You will receive this email", "PAID");
				}
				else if (paid == 2) {
					payment.setStatus(PaymentStatus.CANCLED);
					paymentREPO.save(payment);
					return new MyApiResponse("Ticket is unpaid", "UNPAID");
				}
				
				return new MyApiResponse("Ticket is pending", "PENDING");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		throw new MyNotFoundException("Payment ID not found");
	}
	
	@Override
	public List<PaymentResponse> getAllPaymentsOfUser(String username) {
		Account user = userREPO.getByUsername(username).orElseThrow(() -> new MyNotFoundException("User not found"));
		List<Payment> payments = paymentREPO.findAllByUserId(user.getId());
		
		List<PaymentResponse> resps = new ArrayList<PaymentResponse>();
		for (Payment p : payments)
			resps.add(new PaymentResponse(p));
		return resps;
	}
	
	@Override
	public boolean checkPaymentInfo(PaymentRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void addPaymentMail(Payment payment) {
		PaymentResponse resp = new PaymentResponse(payment);
		this.sendEmail.offer(resp);
	}

	@Scheduled(fixedDelay = SEND_MAIL_SCHEDULE)
	private void sendPaymentViaMail() {
		while (this.sendEmail.size() != 0) {
			PaymentResponse data = this.sendEmail.poll();
			String info = "Payment ID " + data.getId() + "\n" +
							"Total amount: " + data.getPrice()  + "\n" +
							"Create at: " + data.getCreateOn() + "\n" +
							"Movie name: " + data.getDetai().getMovieName() + "\n" +
							"Hall name: " + data.getDetai().getHallName() + "\n" +
							"Start time: " + data.getDetai().getStartTime() + "\n" +
							"Seats: " + String.join(", ", data.getDetai().getSeats());
			String subject = "Movie Project: Payment infomation";
			emailSER.sendMail(data.getEmail(), subject, info);
		}
	}

	@Override
	public String createHash(HashRequest rawdata) {
		try {
			HashUtil hashUtil = new HashUtil();
			String data = rawdata.getBookingId() + "&" + rawdata.getCardID() 
						+ "&" + rawdata.getCardName() + "&" + rawdata.getCVCNumber();
			String hash = hashUtil.calculateHash(data);
			return hash;
			
		} catch (NoSuchAlgorithmException e) {
            return null;
        }
	}
}
