package cinema.ticket.booking.request;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

public class MovieRequest {
	
	
	@Column(name = "title",unique = true)
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "durationInMins")
	private int durationInMins;
	
	@Column(name = "language")
	private String language;
	
	@Column(name = "releaseDate")
	private String releaseDate;
	
	@Column(name = "country")
	private String country;
	
	@JoinColumn(name = "genre")
	@ManyToMany
	private List<String> genre;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "trailer")
	private String trailer;
	
	@Column(name = "actors")
	private String actors;
	
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
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
	public List<String> getGenre() {
		return genre;
	}
	public void setGenre(List<String> genre) {
		this.genre = genre;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

}
