package cinema.ticket.booking.response;

import cinema.ticket.booking.model.CinemaShow;

public class ShowInfoResponse {
	private String id;
	private String hall_name;
	private String hall_id;
	private String movie_name;
	private String movie_id;
	private String start_time;
	private String end_time;
	private int total_seats;
	private int total_reserved_seats;
	private int total_available_seats;
	
	public ShowInfoResponse(CinemaShow show, int total_reserved_seats, int total_available_seats) {
		this.id = show.getId();
		this.hall_name = show.getCinemaHall().getName();
		this.hall_id = show.getCinemaHall().getId();
		this.movie_name = show.getMovie().getTitle();
		this.movie_id = show.getMovie().getId().toString();
		this.start_time = show.getStartTime().toString();
		this.end_time = show.getEndTime().toString();
		this.total_seats = show.getCinemaHall().getCapacity();
		this.total_reserved_seats = total_reserved_seats;
		this.total_available_seats = total_available_seats;
	}
	
	public String getShowID() {
		return this.id;
	}
	
	public String getHallName() {
		return this.hall_name;
	}
		
	public String getHallId() {
		return this.hall_id;
	}

	public String getMovieName() {
		return this.movie_name;
	}

	public String getMovieId() {
		return this.movie_id;
	}

	public String getStartTime() {
		return this.start_time;
	}
	
	public String getEndTime() {
		return this.end_time;
	}
	
	public int getTotalSeats() {
		return this.total_seats;
	}
	
	public int getTotalReversedSeats() {
		return this.total_reserved_seats;
	}
	
	public int getTotalAvailableSeats() {
		return this.total_available_seats;
	}
}
