package com.demoproject.blog.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demoproject.blog.Dto.CategoryDto;
import com.demoproject.blog.Entities.Category;
import com.demoproject.blog.Repositories.CategoryRepo;
import com.demoproject.blog.Service.CategoryService;

import APICallResponse.ResourceNotFoundException;
import ResponseEntityForListwithPagination.CategoryResponse;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepo.save(category);
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(Integer Id, CategoryDto categoryDto) {
		Category foundCategory = categoryRepo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", Id));
		foundCategory.setTitle(categoryDto.getTitle());
		foundCategory.setDescription(categoryDto.getDescription());
		Category savedCategory = categoryRepo.save(foundCategory);
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(Integer Id) {
		Category foundCategory = categoryRepo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", Id));
		return modelMapper.map(foundCategory, CategoryDto.class);
	}

	@Override
	public CategoryResponse getAllCategories(Integer pageNo, Integer Size, String sortBy) {
		Pageable pageable = PageRequest.of(pageNo, Size, Sort.by(sortBy));

		Page<Category> page = categoryRepo.findAll(pageable);
		List<Category> listofCategories = page.getContent();
		List<CategoryDto> listoCategoryDtos = listofCategories.stream()
				.map(eachCategory -> modelMapper.map(eachCategory, CategoryDto.class)).collect(Collectors.toList());
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setListOfCategory(listoCategoryDtos);
		categoryResponse.setPageNo(page.getNumber());
		categoryResponse.setSize(page.getSize());
		categoryResponse.setTotalNoofElements(page.getTotalElements());
		categoryResponse.setTotalNoOfPages(page.getTotalPages());
		return categoryResponse;
	}

	@Override
	public void deleteCategory(Integer Id) {
		Category foundCategory = categoryRepo.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", Id));
		categoryRepo.delete(foundCategory);
	}

	@Override
	public CategoryResponse searchByTitle(String keyword, Integer pageNo, Integer Size, String sortBy) {
		Pageable pageable = PageRequest.of(pageNo, Size, Sort.by(sortBy));

		Page<Category> foundPage = categoryRepo.findByTitleContaining(keyword, pageable);
		List<CategoryDto> listofdtos = foundPage.stream().map(each -> modelMapper.map(each, CategoryDto.class))
				.collect(Collectors.toList());
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setListOfCategory(listofdtos);
		categoryResponse.setPageNo(foundPage.getNumber());
		categoryResponse.setSize(foundPage.getSize());
		categoryResponse.setTotalNoofElements(foundPage.getTotalElements());
		categoryResponse.setTotalNoOfPages(foundPage.getTotalPages());
		return categoryResponse;
	}

}
