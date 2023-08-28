package com.demoproject.blog.Service;

import com.demoproject.blog.Dto.commentDto;

public interface CommentService {
	commentDto createComment(Integer postId, Integer userId, commentDto commentDto);

	void deleteComment(Integer commentId);
}
