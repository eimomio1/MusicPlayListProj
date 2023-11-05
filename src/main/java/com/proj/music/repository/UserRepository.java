package com.proj.music.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	//User findUserByUsername(String userName);

//	Optional<User> findByEmail(String email);

	//Boolean existsByUsername(String userName);

	//Boolean existsByEmail(String email);

	
}
