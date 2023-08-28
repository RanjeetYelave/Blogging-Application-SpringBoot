package com.demoproject.blog.Service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demoproject.blog.Dto.PostDto;
import com.demoproject.blog.Entities.Category;
import com.demoproject.blog.Entities.Post;
import com.demoproject.blog.Entities.User;
import com.demoproject.blog.Repositories.CategoryRepo;
import com.demoproject.blog.Repositories.PostRepo;
import com.demoproject.blog.Repositories.UserRepo;
import com.demoproject.blog.Service.PostService;

import APICallResponse.ResourceNotFoundException;
import ResponseEntityForListwithPagination.PostResponse;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	PostRepo postRepo;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	UserRepo userRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User foundUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
		Category foundCategory = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));
		Post post = modelMapper.map(postDto, Post.class);

		post.setCategory(foundCategory);
		post.setUser(foundUser);
		post.setDatePostAdded(new Date());
		Post savedPost = postRepo.save(post);

		return modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNo, Integer Size, String sortBy) {
		Pageable pageable = PageRequest.of(pageNo, Size, Sort.by(sortBy));

		Page<Post> pagePost = postRepo.findAll(pageable);
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> postDtos = allPosts.stream().map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setListOfPosts(postDtos);
		postResponse.setPageNo(pagePost.getNumber());
		postResponse.setSize(pagePost.getSize());
		postResponse.setTotalNoOfElements(pagePost.getTotalElements());
		postResponse.setTotalNoOfPages(pagePost.getTotalPages());
		return postResponse;
	}

	@Override
	public PostDto getPostByPostId(Integer postId) {
		Post foundPost = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		return modelMapper.map(foundPost, PostDto.class);
	}

	@Override
	public PostResponse getAllPostsByUser(Integer userId, Integer pageNo, Integer Size, String sortBy) {
		Pageable pageable = PageRequest.of(pageNo, Size, Sort.by(sortBy));
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
		Page<Post> pagePost = postRepo.findByUser(user, pageable);
		List<PostDto> listofPostDtos = pagePost.stream().map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setListOfPosts(listofPostDtos);
		postResponse.setPageNo(pagePost.getNumber());
		postResponse.setSize(pagePost.getSize());
		postResponse.setTotalNoOfElements(pagePost.getTotalElements());
		postResponse.setTotalNoOfPages(pagePost.getTotalPages());
		return postResponse;
	}

	@Override
	public PostResponse getAllPostByCategory(Integer categoryId, Integer pageNo, Integer Size, String sortBy) {

		Pageable pageable = PageRequest.of(pageNo, Size, Sort.by(sortBy));
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", categoryId));
		Page<Post> pagePost = postRepo.findByCategory(category, pageable);
		List<PostDto> listofPostDtos = pagePost.stream().map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setListOfPosts(listofPostDtos);
		postResponse.setPageNo(pagePost.getNumber());
		postResponse.setSize(pagePost.getSize());
		postResponse.setTotalNoOfElements(pagePost.getTotalElements());
		postResponse.setTotalNoOfPages(pagePost.getTotalPages());
		return postResponse;
	}

	@Override
	public PostResponse searchByTitle(String postTitle, Integer pageNo, Integer Size, String sortBy) {
		Pageable pageable = PageRequest.of(pageNo, Size, Sort.by(sortBy));
		Page<Post> foundPage = postRepo.findByTitleContaining(postTitle, pageable);
		List<Post> content = foundPage.getContent();
		PostResponse postResponse = new PostResponse();
		List<PostDto> listofDtos = content.stream().map(eachPost -> modelMapper.map(eachPost, PostDto.class))
				.collect(Collectors.toList());
		postResponse.setListOfPosts(listofDtos);
		postResponse.setPageNo(foundPage.getNumber());
		postResponse.setSize(foundPage.getSize());
		postResponse.setTotalNoOfElements(foundPage.getTotalElements());
		postResponse.setTotalNoOfPages(foundPage.getTotalPages());

		return postResponse;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post foundPost = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		foundPost.setTitle(postDto.getTitle());
		foundPost.setImageName(postDto.getImageName());
		foundPost.setDatePostAdded(new Date());
		Post savedPost = postRepo.save(foundPost);

		return modelMapper.map(savedPost, PostDto.class);
	}

}
