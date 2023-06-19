package cinema.ticket.booking.model;

import java.util.Collection;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "AccountTemp",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "username", "email" })
	})
public class AccountTemp implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
	
	@NotBlank
	@NotNull
	@Column(name = "username", length = 128, nullable = false)
    private String username;
	
	@NotBlank
	@NotNull
	@Column(name = "password", nullable = false)
    private String password;
	
	@NotBlank
	@NotNull
	@Column(name = "fullname", nullable = false)
	private String fullname;
	
	@NotBlank
	@NotNull
	@Column(name = "address")
    private String address;
	
	@NotBlank
	@NotNull
	@Email
	@Column(name = "email", length = 128, nullable = false)
    private String email;
	
	@NotBlank
	@NotNull
	@Column(name = "phone")
    private String phone;
	
	@NotBlank
	@NotNull
	@Column(name = "code")
    private String code;
	
	@NotBlank
	@NotNull
	@Column(name = "ip")
    private String ip;
	
	@NotNull
	@Column(name = "times")
    private int times;
	
	@CreationTimestamp
	@Column(name = "create_at", nullable = false, updatable = false)
	private Date create_at;
	
	public AccountTemp() {}
	
	public AccountTemp(Long id, String fullname, 
					String username, String password, 
					String phone, String address, 
					String email, String code, String ip) {
		this.id = id;
		this.fullname = fullname;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.code = code;
		this.times = 0;
		this.ip = ip;
	}
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
	@Override
	public String getPassword() {
		return this.password;
	}
	
	public void setUsername(String username) {
    	this.username = username;
    }

	@Override
	public String getUsername() {
		return this.username;
	}
	
	public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String name) {
        this.fullname = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public int getTimes() {
    	return this.times;
    }
    
    public void setTimes(Integer times) {
    	this.times = times;
    }
    
    public Date getCreateAt() {
    	return this.create_at;
    }
    
    public void setIp(String ip) {
    	this.ip = ip;
    }
    
    public String getIp() {
    	return this.ip;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
