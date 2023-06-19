package cinema.ticket.booking.response;

import cinema.ticket.booking.model.Comment;

public class CommentResponse {
	
	private String comment_id;
	private String username;
	private String comment;
	private long movie_id;
	private int liked;
	private int disliked;
	private int rated_starts;
	private String update_at;
	
	public CommentResponse() {
		this.comment_id = "";
		this.username = "";
		this.comment = "";
		this.movie_id = 1L;
		this.liked = 2;
		this.disliked = 4;
		this.rated_starts = 9;
		this.update_at = "";
	}
	
	public CommentResponse(Comment c) {
		this.comment_id = c.getId();
		this.username = c.getUser().getUsername();
		this.comment = c.getComment();
		this.movie_id = c.getMovie().getId();
		this.liked = c.getLiked();
		this.disliked = c.getDisliked();
		this.rated_starts = c.getRated();
		this.update_at = c.getUpdateAt().toString();
	}
	
	public String getCommentId() {
		return this.comment_id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public Long getMovieId() {
		return this.movie_id;
	}
	
	public int getLiked() {
		return this.liked;
	}
	
	public int getDisliked() {
		return this.disliked;
	}
	
	public int getRatedStarts() {
		return this.rated_starts;
	}
	
	public String getUpdateAt() {
		return this.update_at;
	}
}
