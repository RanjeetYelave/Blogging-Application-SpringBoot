package com.demoproject.blog.Service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoproject.blog.Dto.commentDto;
import com.demoproject.blog.Entities.Comment;
import com.demoproject.blog.Entities.Post;
import com.demoproject.blog.Entities.User;
import com.demoproject.blog.Repositories.CommentRepo;
import com.demoproject.blog.Repositories.PostRepo;
import com.demoproject.blog.Repositories.UserRepo;
import com.demoproject.blog.Service.CommentService;

import APICallResponse.ResourceNotFoundException;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	PostRepo postRepo;
	@Autowired
	CommentRepo commentRepo;
	@Autowired
	UserRepo userRepo;

	@Override
	public commentDto createComment(Integer postId, Integer userId, commentDto commentDto) {
		Post foundPost = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		User foundUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));

		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(foundPost);
		comment.setUser(foundUser);
		commentRepo.save(comment);
		return modelMapper.map(comment, commentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment foundComment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "Id", commentId));
		commentRepo.delete(foundComment);
	}

}
