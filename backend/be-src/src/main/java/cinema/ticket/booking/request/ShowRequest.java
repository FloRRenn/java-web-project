package cinema.ticket.booking.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ShowRequest {
	
	@JsonProperty(value = "cinemaID")
	@NotNull
	@NotBlank
	private String cinemaID;
	
	@JsonProperty(value = "movieID")
	@NotNull
	private Long movieID;
	
	@JsonProperty(value = "start_time")
	@NotNull
	@NotBlank
	private String start_time;

	public String getCinemaId() {
		return this.cinemaID;
	}
	
	public void setCinemaId(String cinemaID) {
		this.cinemaID = cinemaID;
	}
	
	public Long getMovieId() {
		return this.movieID;
	}
	
	public void setMovieId(Long movieID) {
		this.movieID = movieID;
	}
	
	public String getStartTime() {
		return this.start_time;
	}
	
	public void setStartTime(String start_time) {
		this.start_time = start_time;
	}
}
