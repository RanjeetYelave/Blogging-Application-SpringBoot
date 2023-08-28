package com.demoproject.blog.Dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private int id;
	@NotNull(message = "Should not be empty")
	private String name;
	@Email(message = "invalid email")
	private String email;
	@NotNull(message = "should not be empty")
	private String password;

	private String about;

}
