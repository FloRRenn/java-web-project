package cinema.ticket.booking.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "genre",
	uniqueConstraints = { @UniqueConstraint(columnNames = { "genre" ,"id"})
	})
public class Genre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CreatedAt",  updatable = false)
	private Date createdAt;
	
	@UpdateTimestamp
	@Column(name = "lastUpdated")
	private Date lastUpdated;
	
	@Column(name = "genre")	
	private String genre;
	
	@ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Movie> movies;
	
	public List<Movie> getMovies() {
		return movies;
	}

	public void setTitleList(List<Movie> movies) {
		this.movies = movies;
	}

	public Genre() {
	}

	public Genre(Long id, String genre) {
		this.id = id;
		this.genre = genre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
}
