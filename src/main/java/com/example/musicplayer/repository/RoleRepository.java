
package com.example.musicplayer.repository;

import com.example.musicplayer.model.Role;
import com.example.musicplayer.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);	//tìm kiếm theo name, kiểu dữ liệu RoleName
}
