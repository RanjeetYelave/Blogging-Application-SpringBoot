package com.demoproject.blog.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demoproject.blog.Entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
