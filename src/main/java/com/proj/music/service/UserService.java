package com.proj.music.service;

import java.util.List;

import com.proj.music.dto.LoginDto;
import com.proj.music.dto.SignUpDto;
import com.proj.music.entity.User;
import com.proj.music.response.LoginResponse;

public interface UserService {
	public String createUser(SignUpDto userDto);
	public User getUserById(long id);
	public List<User> getUsers();
	public String updateUserById(SignUpDto userDto, long id);
	public String deleteUserById(long id);
	public User findUserByUsername(String username);
	public LoginResponse loginUser(LoginDto loginDto);
}
