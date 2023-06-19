package cinema.ticket.booking.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Comment")
public class Comment {
	
	@Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "cinema.ticket.booking.utils.CustomUUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 26, insertable = false)
    private String id;
	
	@CreationTimestamp
	@Column(name = "create_at", nullable = false, updatable = false)
	private Date create_at;
	
	@UpdateTimestamp
	@Column(name = "update_at", nullable = true, updatable = true)
	private Date update_at;
	
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Account user;
	
	@NotNull
	@Column(name = "rated")
	private int rated;
	
	@NotBlank
	@NotNull
	@Column(name = "comment")
	private String comment;

	@NotNull
	@Column(name = "liked")
	private int liked;
	
	@NotNull
	@Column(name = "disliked")
	private int disliked;
	
	public Comment() {}
	
	public Comment(Movie m, Account u, int r, String c) {
		this.movie = m;
		this.user = u;
		this.rated = r;
		this.comment = c;
		this.liked = 0;
		this.disliked = 0;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Movie getMovie() {
		return this.movie;
	}
	
	public void setMovie(Movie m) {
		this.movie = m;
	}
	
	public Account getUser() {
		return this.user;
	}
	
	public void setUser(Account u) {
		this.user = u;
	}
	
	public int getRated() {
		return this.rated;
	}
	
	public void setRated(int r) {
		this.rated = r;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public void setComment(String c) {
		this.comment = c;
	}
	
	public int getLiked() {
		return this.liked;
	}
	
	public void setLiked(int l) {
		this.liked = l;
	}
	
	public int getDisliked() {
		return this.disliked;
	}
	
	public void setDisliked(int d) {
		this.disliked = d;
	}
	
	public Date getUpdateAt() {
		return this.update_at;
	}
	
	public Date getCreateAt() {
		return this.create_at;
	}
}
