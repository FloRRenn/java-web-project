package cinema.ticket.booking.response;

import cinema.ticket.booking.model.ShowSeat;

public class ShowSeatResponse {
	private String seat_id;
	private String status;
	private String type;
	private String name;
	private int row_index;
	private int col_index;
	private double price;

	public ShowSeatResponse(ShowSeat showSeat) {
		this.seat_id = showSeat.getId();
		this.status = showSeat.getStatus();
		this.type = showSeat.getCinemaSeat().getSeatType();
		this.name = showSeat.getCinemaSeat().getName();
		this.row_index = showSeat.getCinemaSeat().getRowIndex();
		this.col_index = showSeat.getCinemaSeat().getColIndex();
		this.price = showSeat.getCinemaSeat().getPrice();
	}
	
	public String getSeatId() {
		return this.seat_id;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}

	public int getRowIndex() {
		return this.row_index;
	}

	public int getColIndex() {
		return this.col_index;
	}

	public double getPrice() {
		return this.price;
	}
}
