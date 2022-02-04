package com.example.musicplayer.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.musicplayer.model.Role;
import com.example.musicplayer.model.RoleName;
import com.example.musicplayer.repository.RoleRepository;
import com.example.musicplayer.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Optional<Role> findByName(RoleName name) {
		// TODO Auto-generated method stub
		return roleRepository.findByName(name);
	}
	
	
}
