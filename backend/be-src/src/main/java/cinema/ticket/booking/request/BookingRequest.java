	package cinema.ticket.booking.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookingRequest {
	
	@JsonProperty(value = "seat_ids")
	@NotNull
	List<String> seat_ids;
	
	@JsonProperty(value = "show_id")
	@NotNull
	@NotBlank
	String show_id;
	
	public List<String> getSeatsId() {
		return this.seat_ids;
	}
	
	public String getShowId() {
		return this.show_id;
	}
}
