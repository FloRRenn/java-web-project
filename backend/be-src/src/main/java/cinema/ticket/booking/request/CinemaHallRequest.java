package cinema.ticket.booking.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CinemaHallRequest {
	
	@NotBlank
	@NotNull
	private String name;
	
	@NotNull
	private int totalRow;
	
	@NotNull
	private int totalCol;
	
	public String getName() {
		return this.name;
	}
	
	public int getTotalRow() {
		return this.totalRow;
	}
	
	public int getTotalCol() {
		return this.totalCol;
	}
}
