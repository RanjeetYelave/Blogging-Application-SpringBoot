package com.demoproject.blog.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demoproject.blog.Entities.User;
import com.demoproject.blog.Repositories.UserRepo;

import APICallResponse.ResourceNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Username :" + username, 0));
		return user;
	}

}
