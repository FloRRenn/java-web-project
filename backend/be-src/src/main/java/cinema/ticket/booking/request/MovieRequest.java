package cinema.ticket.booking.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ManyToMany;

public class MovieRequest {
	
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("durationInMins")
	private int durationInMins;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("releaseDate")
	private String releaseDate;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("genre")
	@ManyToMany
	private List<String> genre;
	
	@JsonProperty("image")
	private String image;

	@JsonProperty("large_image")
	private String large_image;
	
	@JsonProperty("trailer")
	private String trailer;
	
	@JsonProperty("actors")
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
	public String getLargeImage() {
		return this.large_image;
	}
	public void setLargeImage(String large_image) {
		this.large_image = image;
	}
}
