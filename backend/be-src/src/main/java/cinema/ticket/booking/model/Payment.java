package cinema.ticket.booking.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import cinema.ticket.booking.model.enumModel.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Payment")
public class Payment {
	
	@Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "cinema.ticket.booking.utils.CustomUUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 26, insertable = false)
    private String id;
	
	@OneToOne
    private Booking booking;
	
	@Column(name = "amount")
	private double amount;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
    private PaymentStatus status;
	
	@CreationTimestamp
	@Column(name = "create_at", nullable = false, updatable = false)
	private Date create_at;
	
	@UpdateTimestamp
	@Column(name = "update_at", nullable = true, updatable = true)
	private Date update_at;
	
	public Payment() {}
	
	public Payment(Booking booking, double amount) {
		this.booking = booking;
		this.amount = amount;
		this.status = PaymentStatus.PENDING;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Booking getBooking() {
		return this.booking;
	}
	
	public void setBooking(Booking b) {
		this.booking = b;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public void setSmount(double amount) {
		this.amount = amount;
	}
	
	public PaymentStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
	
	public Date getCreateAt() {
    	return this.create_at;
    }
    
    public Date getUpdateAt() {
    	return this.update_at;
    }
	
	
	public void canclePayment() {
		this.status = PaymentStatus.CANCLED;
	}
	
	public void returnPayment() {
		this.status = PaymentStatus.RETURNED;
	}
}