package com.example.musicplayer.repository;


import com.example.musicplayer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);	//tìm kiếm xem user có tồn tại trong DB không

    Boolean existsByEmail(String email);	//email đã có trong DB chưa khi tạo dữ liệu
    Boolean existsByUsername(String username);	//username đã có trong DB chưa khi tạo dữ liệu

    Iterable<User> findUsersByNameContaining(String user_name);

}
