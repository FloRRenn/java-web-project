package cinema.ticket.booking.model;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "SpamUser")
public class SpamUser {
	
	@Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "cinema.ticket.booking.utils.CustomUUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 26, insertable = false)
    private String id;
	
	@OneToOne
	@NotNull
    private Account user;
	
	@NotNull
	@Column(name = "spamTimes")
	private int spamTimes;
	
	@UpdateTimestamp
	@Column(name = "update_at", nullable = true, updatable = true)
	private Date update_at;
	
	public SpamUser() {}
	
	public SpamUser(Account user) {
		this.user = user;
		this.spamTimes = 1;
	}
	
	public String getId() {
        return id;
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
	
	public int getSpamTimes() {
		return this.spamTimes;
	}
	
	public void setSpamTimes(int times) {
		this.spamTimes = times;
	}
	
	public int increase() {
		this.spamTimes += 1;
		return this.spamTimes;
	}
}




