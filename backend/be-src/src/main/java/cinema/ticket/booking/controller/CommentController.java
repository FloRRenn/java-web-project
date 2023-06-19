package cinema.ticket.booking.controller;



import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.ticket.booking.request.AddCommentRequest;
import cinema.ticket.booking.request.EditCommentRequest;
import cinema.ticket.booking.response.CommentResponse;
import cinema.ticket.booking.response.ErrorResponse;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name="7. Comment Endpoint")

@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/add")
	@Operation(
			summary = "Add comment Service (User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Add comment successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))),
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('USER')")
	public CommentResponse _addComment(Principal principal, @RequestBody @Valid AddCommentRequest req) {
		return commentService.addComment(principal.getName(), req);
	}
	
	/*@PostMapping("/addlist")
	@Operation(
			summary = "Add list comment Service (User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Add list comment successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))),
					@ApiResponse( responseCode = "404", description = "User or Movie is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('USER')")
	public CommentResponse _addComment(Principal principal, @RequestBody @Valid List<AddCommentRequest> req) {
		return commentService.addListComments(principal.getName(), req);
	}
	*/
	
	@GetMapping("/{comment_id}")
	@Operation(
			summary = "Take comment (User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Take comment successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))),
					@ApiResponse( responseCode = "400", description = "This comment is not belonged to you.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse( responseCode = "404", description = "Comment is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('USER')")
	public CommentResponse _getComment(Principal principal, @PathVariable(value = "comment_id") String comment_id) {
		return commentService.getOne(principal.getName(), comment_id);
	}
	
	@GetMapping("/movie/{movie_id}")
	@Operation(
			summary = "Get All Comment From Movie",
			responses = {
					@ApiResponse( responseCode = "200", description = "Get all comment from movie successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))),
			} 
	)
	//@PreAuthorize("hasRole('USER')")
	public List<CommentResponse> _getAllCommentsFromMovie(@PathVariable(value = "movie_id") long movie_id) {
		return commentService.getAllCommentsFromMovieId(movie_id);
	}
	
	@GetMapping("/user/{user_id}")
	@Operation(
			summary = "Get All Comments From User (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Get all comments from user successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class)))
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public List<CommentResponse> _getAllCommentsFromUserId(@PathVariable(value = "user_id") String user_id) {
		return commentService.getAllCommentsFromUserId(user_id);
	}

	@GetMapping("/me")
	@Operation(
			summary = "Get All Comment From Username (User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Get all comment from username successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))),
					@ApiResponse( responseCode = "404", description = "User is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('USER')")
	public List<CommentResponse> _getAllCommentsFromUsername(Principal principal) {
		return commentService.getAllCommentsFromusername(principal.getName());
	}
	
	@GetMapping("/getall")
	@Operation(
			summary = "Get All Comments information (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Get all comment information successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))),
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public List<CommentResponse> _getAllComments() {
		return commentService.getAllComments();
	}
	
	@PutMapping("/{comment_id}/edit")
	@Operation(
			summary = "Edit Comment Service(User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Edit comment successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))),
					@ApiResponse( responseCode = "400", description = "This comment is not belonged to you or Rating number must be in range 0 and 5.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse( responseCode = "404", description = "Comment is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('USER')")
	public CommentResponse _editCommentByUsername(Principal principal, @PathVariable(value = "comment_id") String comment_id, 
			@RequestBody @Valid EditCommentRequest req) {
		return commentService.editComment(principal.getName(), comment_id, req);
	}
	
	@DeleteMapping("/{comment_id}/delete")
	@Operation(
			summary = "Delete Comment Service(User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Delete comment successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
					@ApiResponse( responseCode = "400", description = "This comment is not belonged to you.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse( responseCode = "404", description = "Comment is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('USER')")
	public MyApiResponse _deleteCommentByUsername(Principal principal, @PathVariable(value = "comment_id") String comment_id) {
		return commentService.deleteCommentByUsername(principal.getName(), comment_id);
	}
	
	@DeleteMapping("/{comment_id}/admin_delete")
	@Operation(
			summary = "Admin Delete Comment Service(User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Admin delete comment successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
					@ApiResponse( responseCode = "404", description = "Comment is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public MyApiResponse _deleteCommentById(@PathVariable(value = "comment_id") String comment_id) {
		return commentService.deleteCommentById(comment_id);
	}
}
