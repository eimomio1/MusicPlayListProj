package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.User;
import com.proj.music.response.LoginResponse;

public interface UserService {
	public String createUser(se.michaelthelin.spotify.model_objects.specification.User spotifyUser, String accessToken,
			String refreshToken);

	public User getUserById(long id);

	public List<User> getUsers();

	public String updateUserById(User user, long id);

	public String deleteUserById(long id);
	/* public User findUserByUsername(String userName); */

}
