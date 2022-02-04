package com.example.musicplayer.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.musicplayer.model.User;
import com.example.musicplayer.repository.UserRepository;
import com.example.musicplayer.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Override
	public Optional<User> findByUsername(String name) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(name);
	}

	@Override
	public Boolean existByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmail(email);
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}
	
}
