package com.example.musicplayer.service;

import java.util.Optional;

import com.example.musicplayer.model.Role;
import com.example.musicplayer.model.RoleName;

public interface RoleService {
	Optional<Role> findByName(RoleName name);
}
