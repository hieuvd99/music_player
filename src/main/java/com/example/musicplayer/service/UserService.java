package com.example.musicplayer.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.musicplayer.model.User;

@Service
public interface UserService {
	Optional<User> findByUsername(String name); 	
	Boolean existByUsername(String username);	
	Boolean existByEmail(String email);	
	User save(User user);
}
