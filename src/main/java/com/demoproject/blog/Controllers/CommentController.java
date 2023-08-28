package com.demoproject.blog.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demoproject.blog.Dto.commentDto;
import com.demoproject.blog.Service.impl.CommentServiceImpl;

import APICallResponse.ApiResponse;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	@Autowired
	CommentServiceImpl commentService;

	@PostMapping("/{postId}/{userId}/")
	ResponseEntity<commentDto> createComment(@PathVariable Integer postId, @PathVariable Integer userId,
			@RequestBody commentDto commentDto) {
		com.demoproject.blog.Dto.commentDto createCommentDto = commentService.createComment(postId, userId, commentDto);
		return new ResponseEntity<commentDto>(createCommentDto, HttpStatus.OK);
	}

	@DeleteMapping("/{commentId}")
	ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted", true, "DELETED FROM SERVER"),
				HttpStatus.GONE);
	}
}
