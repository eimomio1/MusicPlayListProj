package com.proj.music.service;

import java.util.List;

import se.michaelthelin.spotify.model_objects.specification.User;

public interface UserService {

	public String createUser(User spotifyUser, String accessToken, String refreshToken);

	public User getUserById(long id);

	public List<User> getUsers();

//	public String updateUserById(SignUpDto userDto, long id);

	public String deleteUserById(long id);

	public String updateUserById(User user, long id);
}
