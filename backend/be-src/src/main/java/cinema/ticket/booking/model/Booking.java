package cinema.ticket.booking.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import cinema.ticket.booking.model.enumModel.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Booking")
public class Booking {
	
	@Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "cinema.ticket.booking.utils.CustomUUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 26, insertable = false)
    private String id;
	
	@ManyToOne
	@NotNull
    private Account user;
	
	@ManyToOne
	@NotNull
    private CinemaShow show;
	
	@CreationTimestamp
	@Column(name = "create_at", nullable = false, updatable = false)
	private Date create_at;
	
	@UpdateTimestamp
	@Column(name = "update_at", nullable = true, updatable = true)
	private Date update_at;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
    private BookingStatus status;
	
	@ManyToMany(fetch = FetchType.EAGER)
    private List<ShowSeat> seats;
	
	public Booking() {}
	
	public Booking(Booking booking) {
		this.user = booking.getUser();
		this.show = booking.getShow();
		this.seats = booking.getSeats();
		this.status = BookingStatus.PENDING;
	}
	
	public Booking(Account user, CinemaShow show, List<ShowSeat> seats) {
		this.user = user;
		this.show = show;
		this.seats = seats;
		this.status = BookingStatus.PENDING;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Account getUser() {
		return this.user;
	}
	
	public void setUser(Account user) {
		this.user = user;
	}
	
	public CinemaShow getShow() {
		return this.show;
	}
	
	public void setShow(CinemaShow show) {
		this.show = show;
	}
	
	public BookingStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(BookingStatus status) {
		this.status = status;
	}
	
	public Date getCreateAt() {
    	return this.create_at;
    }
    
    public Date getUpdateAt() {
    	return this.update_at;
    }
	
	public List<ShowSeat> getSeats() {
		return this.seats;
	}
	
	public void setSeats(List<ShowSeat> seats) {
		this.seats = seats;
	}
	
	public void addSeat(ShowSeat seat) {
		this.seats.add(seat);
	}
	
	public void removeSeat(ShowSeat seat) {
		this.seats.remove(seat);
	}
	
	public boolean isEmptySeats() {
		return this.seats.isEmpty();
	}
 	
	public List<String> getNameOfSeats() {
		List<String> names = new ArrayList<>();
		for (ShowSeat seat : this.seats)
			names.add(seat.getCinemaSeat().getName());
		return names;
	}
	
	public double getPriceFromListSeats() {
		double res = 0;
		for (ShowSeat seat : this.seats)
			res += seat.getCinemaSeat().getPrice();
		return res;
	}
}








