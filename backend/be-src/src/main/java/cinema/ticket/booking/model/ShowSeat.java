package cinema.ticket.booking.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import cinema.ticket.booking.model.enumModel.ESeatStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ShowSeat")
public class ShowSeat {
	@Id
    @GeneratedValue(generator = "custom-uuid-1")
    @GenericGenerator(name = "custom-uuid-1", strategy = "cinema.ticket.booking.utils.CustomRandomStringID")
	@Column(name = "id", unique = true, nullable = false, length = 26, insertable = false)
    private String id;

    @ManyToOne
    private CinemaShow show;

    @ManyToOne
    private CinemaSeat cinemaSeat;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ESeatStatus status;
    
    @CreationTimestamp
	@Column(name = "create_at", nullable = false, updatable = false)
	private Date create_at;
	
	@UpdateTimestamp
	@Column(name = "update_at", nullable = true, updatable = true)
	private Date update_at;

    public ShowSeat() {}

    public ShowSeat(CinemaShow show, CinemaSeat cinemaSeat, ESeatStatus status) {
        this.show = show;
        this.cinemaSeat = cinemaSeat;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CinemaShow getShow() {
        return show;
    }

    public void setShow(CinemaShow show) {
        this.show = show;
    }

    public CinemaSeat getCinemaSeat() {
        return cinemaSeat;
    }

    public void setCinemaSeat(CinemaSeat cinemaSeat) {
        this.cinemaSeat = cinemaSeat;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(ESeatStatus status) {
        this.status = status;
    }
}