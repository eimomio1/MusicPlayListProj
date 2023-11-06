package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proj.music.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	// User findUserByUsername(String userName);

//	Optional<User> findByEmail(String email);

	// Boolean existsByUsername(String userName);

	// Boolean existsByEmail(String email);
	Users findByRefId(String refId);
}
