package com.demoproject.blog.Controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demoproject.blog.Configs.AppConstants;
import com.demoproject.blog.Dto.PostDto;
import com.demoproject.blog.Service.impl.FileServiceImpl;
import com.demoproject.blog.Service.impl.PostServiceImpl;

import ResponseEntityForListwithPagination.PostResponse;

@RestController
@RequestMapping("api/post/")
public class PostController {
	@Autowired
	PostServiceImpl postservice;

	@Autowired
	FileServiceImpl fileService;

	@Value("${image.source}")
	String path;

	@PostMapping("/{userId}/{categoryId}/")
	ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createdPost = postservice.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);

	}

	@GetMapping("/")
	ResponseEntity<PostResponse> getallposts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
			@RequestParam(value = "Size", defaultValue = AppConstants.SIZE, required = false) Integer Size,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {
		PostResponse allPosts = postservice.getAllPosts(pageNo, Size, sortBy);
		return new ResponseEntity<>(allPosts, HttpStatus.OK);
	}

	@GetMapping("byUser/{userId}")
	ResponseEntity<PostResponse> getallpostsbyUser(@PathVariable Integer userId,
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
			@RequestParam(value = "Size", defaultValue = AppConstants.SIZE, required = false) Integer Size,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {
		PostResponse allPostsByUser = postservice.getAllPostsByUser(userId, pageNo, Size, sortBy);
		return new ResponseEntity<>(allPostsByUser, HttpStatus.OK);
	}

	@GetMapping("byCategory/{categoryId}")
	ResponseEntity<PostResponse> getallpostsbyCategory(@PathVariable Integer categoryId,
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
			@RequestParam(value = "Size", defaultValue = AppConstants.SIZE, required = false) Integer Size,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {
		PostResponse allPostByCategory = postservice.getAllPostByCategory(categoryId, pageNo, Size, sortBy);
		return new ResponseEntity<>(allPostByCategory, HttpStatus.OK);
	}

	@GetMapping("/{postId}")
	ResponseEntity<PostDto> getallpostsbyPostId(@PathVariable Integer postId) {
		PostDto postByPostId = postservice.getPostByPostId(postId);
		return new ResponseEntity<>(postByPostId, HttpStatus.OK);
	}

	@GetMapping("search/{title}")
	ResponseEntity<PostResponse> searchByTitle(@PathVariable String title,
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNO,
			@RequestParam(value = "Size", defaultValue = AppConstants.SIZE, required = false) Integer Size,
			@RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
		PostResponse searchByTitle = postservice.searchByTitle(title, pageNO, Size, sortBy);
		return new ResponseEntity<>(searchByTitle, HttpStatus.OK);

	}

	@PostMapping("image/upload/{postId}")
	ResponseEntity<PostDto> uploadImage(@PathVariable Integer postId, @RequestParam MultipartFile image)
			throws IOException {
		PostDto postByPostId = postservice.getPostByPostId(postId);
		String imageName = fileService.uploadImage(path, image);
		postByPostId.setImageName(imageName);
		PostDto updatedPost = postservice.updatePost(postByPostId, postId);
		return new ResponseEntity<>(updatedPost, HttpStatus.OK);

	}
}
