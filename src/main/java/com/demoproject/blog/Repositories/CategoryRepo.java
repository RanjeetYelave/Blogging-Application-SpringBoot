package com.demoproject.blog.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demoproject.blog.Entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	Page<Category> findByTitleContaining(String keyword, Pageable pageable);
}
