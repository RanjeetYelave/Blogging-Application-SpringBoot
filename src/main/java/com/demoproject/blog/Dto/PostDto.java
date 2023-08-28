package com.demoproject.blog.Dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private int id;

	private String title;
	private String imageName;
	private Date datePostAdded;

	private UserDto user;

	private CategoryDto category;
	private List<commentDto> comments = new ArrayList<>();

}
