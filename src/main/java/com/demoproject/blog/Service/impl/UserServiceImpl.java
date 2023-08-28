package com.demoproject.blog.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demoproject.blog.Dto.UserDto;
import com.demoproject.blog.Entities.User;
import com.demoproject.blog.Repositories.UserRepo;
import com.demoproject.blog.Service.UserService;

import APICallResponse.ResourceNotFoundException;
import ResponseEntityForListwithPagination.UserResponse;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo; // repo object
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userdto) { // method overriden from interface
		User user = this.DtotoUser(userdto); // convert dto to user

		String simplePassword = userdto.getPassword();
		String encodedPassword = passwordEncoder.encode(simplePassword);
		user.setPassword(encodedPassword);
		User savedUser = this.userRepo.save(user); // saved in repo
		System.out.println("Created user : /api/users/create");
		return this.UsertoDto(savedUser); // returning dto obj converted back
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		this.UsertoDto(user);

		return userDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.UsertoDto(user);
	}

	@Override
	public UserResponse getAllUsers(Integer pageNo, Integer Size, String sortBy, String sortType) {
		PageRequest pageRequest = null;
		if (sortType.equalsIgnoreCase("asc")) {
			pageRequest = PageRequest.of(pageNo, Size, Sort.by(sortBy).ascending());
		} else {
			pageRequest = PageRequest.of(pageNo, Size, Sort.by(sortBy).descending());
		}
		Pageable pageable = pageRequest;
		Page<User> page = userRepo.findAll(pageable);
		List<User> allUsers = page.getContent();
		List<UserDto> dtoList = allUsers.stream().map(user -> this.UsertoDto(user)).collect(Collectors.toList());

		UserResponse userResponse = new UserResponse();
		userResponse.setListOfUsers(dtoList);
		userResponse.setPageNo(page.getNumber());
		userResponse.setSize(page.getSize());
		userResponse.setTotalNoOfElements(page.getTotalElements());
		userResponse.setTotalNoOfPages(page.getTotalPages());

		return userResponse;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}

	private User DtotoUser(UserDto userdto) {
		User user = this.modelMapper.map(userdto, User.class);

//		user.setId(userdto.getId());
//		user.setName(userdto.getName());
//		user.setEmail(userdto.getEmail());
//		user.setPassword(userdto.getPassword());
//		user.setAbout(userdto.getAbout());
		return user;
	}

	private UserDto UsertoDto(User user) {

		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		// UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}

	@Override
	public UserResponse searchByKeyword(String keyword, Integer pageNo, Integer Size, String sortBy) {
		Pageable pageable = PageRequest.of(pageNo, Size, Sort.by(sortBy));
		Page<User> foundPage = userRepo.findBynameContaining(keyword, pageable);
		List<UserDto> collecteDtos = foundPage.stream().map(eachUser -> modelMapper.map(eachUser, UserDto.class))
				.collect(Collectors.toList());
		UserResponse userResponse = new UserResponse();
		userResponse.setListOfUsers(collecteDtos);
		userResponse.setPageNo(foundPage.getNumber());
		userResponse.setSize(foundPage.getSize());
		userResponse.setTotalNoOfElements(foundPage.getTotalElements());
		userResponse.setTotalNoOfPages(foundPage.getTotalPages());

		return userResponse;
	}

}
