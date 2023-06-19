package cinema.ticket.booking.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddCommentRequest {
	
	@NotNull
	@JsonProperty("movie_id")
	private long movie_id;
	
	@NotNull
	@NotBlank
	@JsonProperty("comment")
	private String comment;
	
	@NotNull
	@JsonProperty("rating")
	private int rated_stars;
	
	public AddCommentRequest() {}
	
	public long getMovieId() {
		return this.movie_id;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public int getRatedStars() {
		return this.rated_stars;
	}
}
