package cinema.ticket.booking.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import cinema.ticket.booking.utils.DateUtils;

@Entity
@Table(name = "CinemaShow")
public class CinemaShow {
	@Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "cinema.ticket.booking.utils.CustomUUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 26, insertable = false)
    private String id;

    @ManyToOne
    private CinemaHall cinemaHall;

    @ManyToOne
    private Movie movie;
    
    @Column(name = "start_time")
    @NotNull
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @CreationTimestamp
	@Column(name = "create_at", nullable = false, updatable = false)
	private Date create_at;
	
	@UpdateTimestamp
	@Column(name = "update_at", nullable = true, updatable = true)
	private Date update_at;

    public CinemaShow() {}

    public CinemaShow(CinemaHall cinemaHall, Movie movie, LocalDateTime startTime, LocalDateTime endTime) {
        this.cinemaHall = cinemaHall;
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = endTime;
//        this.endTime = startTime.plusMinutes(movie.getDurationInMins()).plusMinutes(10);
    }
    
    public CinemaShow(CinemaHall cinemaHall, Movie movie, String startTimeString) {
    	this.cinemaHall = cinemaHall;
        this.movie = movie;
        this.startTime = DateUtils.convertStringDateToDate(startTimeString, "dd/MM/yyyy HH:mm");;
        this.endTime = startTime.plusMinutes(movie.getDurationInMins()).plusMinutes(10);
    }

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public Date getCreateAt() {
    	return this.create_at;
    }
    
    public Date getUpdateAt() {
    	return this.update_at;
    }
}