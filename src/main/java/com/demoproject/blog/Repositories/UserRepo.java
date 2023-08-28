package com.demoproject.blog.Repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demoproject.blog.Entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Page<User> findBynameContaining(String name, Pageable pageable);

	Optional<User> findByEmail(String email);

}
