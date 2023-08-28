package com.demoproject.blog.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demoproject.blog.Configs.AppConstants;
import com.demoproject.blog.Dto.UserDto;
import com.demoproject.blog.Service.UserService;

import APICallResponse.ApiResponse;
import APICallResponse.ResponseMessage;
import ResponseEntityForListwithPagination.UserResponse;

@RestController
@RequestMapping("/api/users")
public class userController {
	@Autowired
	private UserService userService;
	ResponseMessage responsemessage = new ResponseMessage();

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUser = userService.createUser(userDto);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

	}

	@GetMapping("/")
	public ResponseEntity<UserResponse> getUser(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
			@RequestParam(value = "Size", defaultValue = AppConstants.SIZE, required = false) Integer Size,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortType", defaultValue = "asc", required = false) String sortType) {

		UserResponse allUsers = userService.getAllUsers(pageNo, Size, sortBy, sortType);
		return new ResponseEntity<>(allUsers, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{Id}")
	public ResponseEntity<UserDto> getUserbyId(@PathVariable Integer Id) {
		UserDto userById = userService.getUserById(Id);
		return new ResponseEntity<>(userById, HttpStatus.ACCEPTED);
	}

	@PutMapping("{Id}")
	public ResponseEntity<UserDto> UpdateuserById(@Valid @RequestBody UserDto userdto, @PathVariable Integer Id) {
		UserDto updatedUser = userService.updateUser(userdto, Id);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("{Id}")
	public ResponseEntity<ApiResponse> DeleteUserById(@PathVariable Integer Id) {
		userService.deleteUser(Id);

		return new ResponseEntity<>(new ApiResponse("Deletion Successful", true, "ERR CODE: 200: Truncated"),
				HttpStatus.CREATED);
	}

	@GetMapping("search/{keyword}")
	ResponseEntity<UserResponse> searchByTitle(@PathVariable String keyword,
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
			@RequestParam(value = "Size", defaultValue = AppConstants.SIZE, required = false) Integer Size,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {
		UserResponse searchByKeyword = userService.searchByKeyword(keyword, pageNo, Size, sortBy);
		return new ResponseEntity<UserResponse>(searchByKeyword, HttpStatus.OK);
	}
}