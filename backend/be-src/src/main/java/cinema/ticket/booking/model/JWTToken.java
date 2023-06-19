package cinema.ticket.booking.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Token")
public class JWTToken {
	
	@Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "cinema.ticket.booking.utils.CustomUUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 26, insertable = false)
    private String id;
	
	@OneToOne
	private Account user;
	
	@Column(name = "refresh_token", length = 3000)
	@NotBlank
	@NotNull
	private String refreshToken;
	
	@Column(name = "access_token", length = 3000)
	@NotBlank
	@NotNull
	private String accessToken;
	
	public JWTToken() {}
	
	public JWTToken(Account user, String accessToken, String refreshToken) {
		this.user = user;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
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
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public void setAccessToken(String token) {
		this.accessToken = token;
	}
	
	public String getRefreshToken() {
		return this.refreshToken;
	}
	
	public void setRefreshToken(String token) {
		this.refreshToken = token;
	}
}



