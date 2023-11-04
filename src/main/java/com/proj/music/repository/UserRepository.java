package com.proj.music.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findUserByUsername(String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
	Optional<User> findByUsernameOrEmailAndPass(String username, String email, String password);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
