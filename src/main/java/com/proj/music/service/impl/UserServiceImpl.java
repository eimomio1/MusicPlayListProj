package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.proj.music.entity.User;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.UserRepository;
import com.proj.music.response.LoginResponse;
import com.proj.music.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public String createUser(se.michaelthelin.spotify.model_objects.specification.User spotifyUser, String accessToken,
			String refreshToken) {

		// Create a new User object
		User user = new User();

		// Set the properties of the User object
		user.setEmail(spotifyUser.getEmail()); // Use the email from the Spotify user
		user.setUserName(spotifyUser.getDisplayName()); // Use the username from the Spotify user
		user.setAccessToken(accessToken);
		user.setRefreshToken(refreshToken);

		// Save the user to the database
		userRepository.save(user);

		return "User has been saved";

	}

	@Override
	public User getUserById(long id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public String updateUserById(User user, long id) {
		Optional<User> findUser = userRepository.findById(id);

		if (findUser.isPresent()) {
			findUser.get().setId(user.getId());
			findUser.get().setUserName(user.getUserName());
		} else {
			throw new ResourceNotFoundException("User +" + id + "+Not Found");
		}

		return "User has been updated";
	}

	@Override
	public String deleteUserById(long id) {
		Optional<User> findUser = userRepository.findById(id);
		if (findUser.isPresent()) {
			userRepository.deleteById(id);
		}
		return "User has been deleted";
	}

	/*
	 * @Override public User findUserByUsername(String userName) { return
	 * userRepository.findUserByUsername(userName); }
	 */

}
