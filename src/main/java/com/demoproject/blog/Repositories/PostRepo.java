package com.demoproject.blog.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demoproject.blog.Entities.Category;
import com.demoproject.blog.Entities.Post;
import com.demoproject.blog.Entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	Page<Post> findByCategory(Category category, Pageable pageable);

	Page<Post> findByUser(User user, Pageable pageable);

	Page<Post> findByTitleContaining(String postTitle, Pageable pageable);

}
