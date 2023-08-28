package com.demoproject.blog.Service;

import com.demoproject.blog.Dto.UserDto;

import ResponseEntityForListwithPagination.UserResponse;

public interface UserService {
	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	UserResponse getAllUsers(Integer pageNo, Integer Size, String sortBy, String sortType);

	void deleteUser(Integer userId);

	UserResponse searchByKeyword(String keyword, Integer pageNo, Integer Size, String sortBy);

}
