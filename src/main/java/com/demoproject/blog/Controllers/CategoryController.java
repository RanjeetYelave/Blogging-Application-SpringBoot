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
import com.demoproject.blog.Dto.CategoryDto;
import com.demoproject.blog.Service.impl.CategoryServiceImpl;

import APICallResponse.ApiResponse;
import ResponseEntityForListwithPagination.CategoryResponse;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	CategoryServiceImpl categoryService;

	@PostMapping("/")
	ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createdCategory = categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
	}

	@PutMapping("/{Id}")
	ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer Id) {
		CategoryDto updatedCategoryDto = categoryService.updateCategory(Id, categoryDto);
		return new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
	}

	@GetMapping("/")
	ResponseEntity<CategoryResponse> getallCategories(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
			@RequestParam(value = "Size", defaultValue = AppConstants.SIZE, required = false) Integer Size,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {
		CategoryResponse allCategories = categoryService.getAllCategories(pageNo, Size, sortBy);
		return new ResponseEntity<>(allCategories, HttpStatus.OK);
	}

	@GetMapping("/{Id}")
	ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer Id) {
		CategoryDto categoryDto = categoryService.getCategoryById(Id);
		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
	}

	@DeleteMapping("/{Id}")
	ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer Id) {
		categoryService.deleteCategory(Id);
		return new ResponseEntity<>(new ApiResponse("Deleted Successfully", true, "Gone"), HttpStatus.GONE);
	}

	@GetMapping("/search/{keyword}")
	ResponseEntity<CategoryResponse> searchByKeyword(@PathVariable String keyword,
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
			@RequestParam(value = "Size", defaultValue = AppConstants.SIZE, required = false) Integer Size,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {
		CategoryResponse foundCategoryResponse = categoryService.searchByTitle(keyword, pageNo, Size, sortBy);
		return new ResponseEntity<>(foundCategoryResponse, HttpStatus.OK);
	}

}
