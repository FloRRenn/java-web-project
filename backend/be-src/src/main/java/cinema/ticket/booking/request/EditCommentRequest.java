package cinema.ticket.booking.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EditCommentRequest {
	
	@NotNull
	@NotBlank
	@JsonProperty("comment")
	private String comment;
	
	@NotNull
	@JsonProperty("rating")
	private int rating_stars;
	
	public EditCommentRequest() {}
	
	public String getComment() {
		return this.comment;
	}
	
	public int getRatingStars() {
		return this.rating_stars;
	}
}
