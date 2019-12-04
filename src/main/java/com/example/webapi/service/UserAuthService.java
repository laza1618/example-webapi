package com.example.webapi.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.webapi.exceptions.RessourceNotFoundException;
import com.example.webapi.model.User;
import com.example.webapi.repository.UserRepository;

@Service
public class UserAuthService implements UserDetailsService {
	
	@Autowired
	UserRepository repo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findUserByUsername(username).orElseThrow(
				() -> new RessourceNotFoundException("User", "id", 0));
		return user;
	}

}
