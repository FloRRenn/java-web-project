package cinema.ticket.booking.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SeatEditRequest {
	
	@NotNull
	private int row;
	
	@NotNull
	private int col;
	
	@NotBlank
	@NotNull
	private String type;
	
	@NotBlank
	@NotNull
	private String status;
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getStatus() {
		return this.status;
	}
}
