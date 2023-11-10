package com.proj.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.proj.music.service.SongService;
import com.proj.music.service.UserService;

import se.michaelthelin.spotify.SpotifyApi;

@RestController
public class SongController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SpotifyApi spotifyApi;
	
	@Autowired
	private SongService songService;
	
	
	
}
