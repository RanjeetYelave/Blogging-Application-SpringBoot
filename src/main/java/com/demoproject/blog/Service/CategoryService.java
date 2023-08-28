package com.demoproject.blog.Service;

import com.demoproject.blog.Dto.CategoryDto;

import ResponseEntityForListwithPagination.CategoryResponse;

public interface CategoryService {
	// create
	CategoryDto createCategory(CategoryDto categoryDto);

	// update
	CategoryDto updateCategory(Integer Id, CategoryDto categoryDto);

	// get
	CategoryDto getCategoryById(Integer Id);

	// getall
	CategoryResponse getAllCategories(Integer pageNo, Integer Size, String sortBy);

	// delete
	void deleteCategory(Integer Id);

	public CategoryResponse searchByTitle(String keyword, Integer pageNo, Integer Size, String sortBy);

}
