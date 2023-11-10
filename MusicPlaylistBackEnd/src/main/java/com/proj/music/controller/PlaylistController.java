package com.proj.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.proj.music.entity.Users;
import com.proj.music.service.PlaylistService;
import com.proj.music.service.UserService;
import com.proj.music.spotify.config.SpotifyConfiguration;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.detailed.BadRequestException;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/playlist")
@CrossOrigin(origins = "http://localhost:4200") // Add your frontend URL
public class PlaylistController {
	 
	private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	private SpotifyConfiguration spotifyConfiguration;

	@PostMapping("/create-playlist/users/{username}/playlists")
	public ResponseEntity<String> createPlaylist(@RequestBody String nameOfPlaylist, @PathVariable String username) {
	    System.out.println("Received request to create playlist");
	    
	    try {
	        // Retrieve the Users object from the repository
	        Users users = userService.findRefById(username);
	        logger.info("Users: {}", users);
	        logger.info("Name of Playlist: {}", nameOfPlaylist);
	        logger.info("Username: {}", username);

	        if (users != null) {
	            SpotifyApi spotifyApi = spotifyConfiguration.getSpotifyObject();
	            spotifyApi.setAccessToken(users.getAccessToken());
	            spotifyApi.setRefreshToken(users.getRefreshToken());

	            // Create a playlist on Spotify
	            final CreatePlaylistRequest.Builder playlistBuilder = spotifyApi.createPlaylist(username, nameOfPlaylist);

	            // Log the request payload
	            logger.info("Playlist Request Payload: {}", playlistBuilder.build().getBody());

	            final CreatePlaylistRequest playlistRequest = playlistBuilder.build();
	            
	            Playlist newPlaylist = playlistRequest.execute();
	            // Saves playlist to database table
	            playlistService.addPlaylist(newPlaylist);

	            return new ResponseEntity<>("Playlist has been created", HttpStatus.CREATED);
	        } else {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (BadRequestException e) {
	        // Log the detailed error information
	        logger.error("Error creating playlist: {}", e.getMessage());

	        e.printStackTrace();  // This will print the stack trace for more details
	        return new ResponseEntity<>("Failed to create playlist: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    } catch (Exception e) {
	        logger.error("Failed to create playlist", e);
	        // Handle exception, log or return an error response

	        return new ResponseEntity<>("Failed to create playlist: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	   
		
		
	
	

	
	
	
	
	
	
	
}