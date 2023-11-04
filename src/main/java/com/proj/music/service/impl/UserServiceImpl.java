package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proj.music.dto.LoginDto;
import com.proj.music.dto.SignUpDto;
import com.proj.music.entity.User;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.UserRepository;
import com.proj.music.response.LoginResponse;
import com.proj.music.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String createUser(SignUpDto userDto) {
		User user = new User();
		user.setFullName(userDto.getFirstName() + " " + userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setUserName(userDto.getUsername());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userRepository.save(user);
		return "User has been saved"; 
	}
	
	@Override
	public LoginResponse loginUser(LoginDto loginDto)
	{
		Optional<User> user = userRepository.findByEmail(loginDto.getUsernameOrEmail());
		if(user.isPresent())
		{
			String password = loginDto.getPassword();
			String encodedPassword = user.get().getPassword();
			Boolean isPasswordRight = passwordEncoder.matches(password, encodedPassword);
			if(isPasswordRight)
			{
				Optional<User> user1 = userRepository.findByUsernameOrEmailAndPass(loginDto.getUsernameOrEmail(), password, encodedPassword);
				if(user1.isPresent())
				{
					return new LoginResponse("Login Success", true);
				}
				else {
					return new LoginResponse("Login Failed", false);
				}
			}
			else {
				return new LoginResponse("password Not Match", false);
			}
		}
		else {
			return new LoginResponse("Email doesnt exist", false);
		}
	}
	
	@Override
	public User getUserById(long id) {	
		return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public String updateUserById(SignUpDto userDto, long id) {
		Optional<User> findUser = userRepository.findById(id);
		
		if(findUser.isPresent())
		{
			findUser.get().setId(userDto.getId());
			findUser.get().setFullName(userDto.getFirstName() + " " + userDto.getLastName());
			findUser.get().setUserName(userDto.getUsername());
			findUser.get().setPassword(userDto.getPassword());
		}
		else {
			throw new ResourceNotFoundException("User +" + id + "+Not Found");
		}
		
		return "User has been updated";
	}

	@Override
	public String deleteUserById(long id) {
		Optional<User> findUser = userRepository.findById(id);
		if(findUser.isPresent())
		{
			userRepository.deleteById(id);
		}
		return "User has been deleted";
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

}
