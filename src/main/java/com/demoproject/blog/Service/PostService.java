package com.demoproject.blog.Service;

import com.demoproject.blog.Dto.PostDto;

import ResponseEntityForListwithPagination.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	PostDto updatePost(PostDto postDto, Integer postId);

	PostResponse getAllPosts(Integer pageNo, Integer Size, String sortBy);

	PostDto getPostByPostId(Integer postId);

	PostResponse getAllPostsByUser(Integer userId, Integer pageNo, Integer Size, String sortBy);

	PostResponse getAllPostByCategory(Integer categoryId, Integer pageNo, Integer Size, String sortBy);

	PostResponse searchByTitle(String postTitle, Integer pageNo, Integer Size, String sortBy);

}
