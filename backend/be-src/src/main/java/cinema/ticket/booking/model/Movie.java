package cinema.ticket.booking.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import cinema.ticket.booking.request.MovieRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Movie",
	uniqueConstraints = { @UniqueConstraint(columnNames = { "title" ,"id"})
	})

public class Movie{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CreatedAt", updatable = false)
	private Date createdAt;
	
	@UpdateTimestamp
	@Column(name = "lastUpdated")
	private Date lastUpdated;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description", length = 3000)
	private String description;
	
	@Column(name = "durationInMins")
	private int durationInMins;
	
	@Column(name = "language")
	private String language;
	
	@Column(name = "releaseDate")
	private String releaseDate;
	
	@Column(name = "country")
	private String country;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	@JoinTable(name = "Movie_Genre", 
		joinColumns = {
				@JoinColumn(name = "movie_id", referencedColumnName = "id")
				
		},
		inverseJoinColumns = {
				@JoinColumn(name = "genre_id", referencedColumnName = "id")
		}
		)
	@JsonProperty(value = "genres")
	private List<Genre> genres;
	
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "trailer")
	private String trailer;
	
	@Column(name = "actors")
	private String actors;
	
	public Movie() {}
	
	public Movie(MovieRequest req) {
		this.title = req.getTitle();
		this.description = req.getDescription();
		this.durationInMins = req.getDurationInMins();
		this.language = req.getLanguage();
		this.releaseDate = req.getReleaseDate();
		this.country = req.getCountry();
		this.image = req.getImage();
	}
	
	public String getTrailer() {
		return trailer;
	}
	
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	
	public String getActors() {
		return actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void settitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getDurationInMins() {
		return durationInMins;
	}
	
	public void setDurationInMins(int durationInMins) {
		this.durationInMins = durationInMins;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public List<Genre> getGenres() {
		return genres;
	}
	
	public void setGenres(List<Genre> genre) {
		this.genres = genre;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public List<Comment> getComments() {
		return this.comments;
	}
	
	public void setComments(List<Comment> c) {
		this.comments = c;
	}
	
	public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }
}
