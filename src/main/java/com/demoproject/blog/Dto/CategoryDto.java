package com.demoproject.blog.Dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	@NotNull
	private int id;
	@NotEmpty
	private String Title;
	@Size(max = 10000, min = 10, message = "Description must be between 10 and 10000")
	private String Description;
}
