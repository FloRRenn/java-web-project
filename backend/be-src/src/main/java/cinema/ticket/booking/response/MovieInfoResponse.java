package cinema.ticket.booking.response;

import java.util.ArrayList;
import java.util.List;

import cinema.ticket.booking.model.Comment;
import cinema.ticket.booking.model.Genre;
import cinema.ticket.booking.model.Movie;

public class MovieInfoResponse {
	private Long id;
	private String title;
	private String description;
	private int durationInMins;
	private String language;
	private String releaseDate;
	private String country;
	private String image;
	private String trailer;
	private String actors;
	private List<Genre> genres;
	private List<CommentResponse> comments;
	
	public MovieInfoResponse(Movie m) {
		this.id = m.getId();
		this.title = m.getTitle();
		this.description = m.getDescription();
		this.language = m.getLanguage();
		this.releaseDate = m.getReleaseDate();
		this.country = m.getCountry();
		this.trailer = m.getTrailer();
		this.actors = m.getActors();
		this.image = m.getImage();
		this.genres = m.getGenres();
		this.durationInMins = m.getDurationInMins();
		this.comments = this.convertType(m.getComments());
	}

	public MovieInfoResponse(Long id, String title, String image, List<Genre> genre, int durationInMins, List<Comment> comment) {
		this.id = id;
		this.title = title;
		this.image = image;
		this.genres = genre;
		this.durationInMins = durationInMins;
		this.comments = this.convertType(comment);
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return this.description;
	}

	public String getLanguage() {
		return this.language;
	}

	public String getreleaseDate() {
		return this.releaseDate;
	}

	public String getCountry() {
		return this.country;
	}

	public String getTrailer() {
		return this.trailer;
	}

	public String getActors() {
		return this.actors;
	}

	public String getImage() {
		return image;
	}


	public List<Genre> getGenres() {
		return genres;
	}

	public int getDurationInMins() {
		return durationInMins;
	}
	
	public List<CommentResponse> getComments() {
		return this.comments;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public void setGenre(List<Genre> genre) {
		this.genres = genre;
	}


	public void setDurationInMins(int durationInMins) {
		this.durationInMins = durationInMins;
	}
	
	private List<CommentResponse> convertType(List<Comment> comments) {
		List<CommentResponse> data = new ArrayList<CommentResponse>();
		for (Comment c : comments)
			data.add(new CommentResponse(c));
		return data;
	}
	
}
