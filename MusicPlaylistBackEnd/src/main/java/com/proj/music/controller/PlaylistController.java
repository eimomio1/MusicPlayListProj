package com.proj.music.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.proj.music.entity.Users;
import com.proj.music.service.PlaylistService;
import com.proj.music.service.UserService;
import com.proj.music.spotify.config.SpotifyConfiguration;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;


@RestController
@RequestMapping("/playlist")
@CrossOrigin(origins = "http://localhost:4200") // Add your frontend URL
public class PlaylistController {
     
	@Autowired
	UserService userService;
	
	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	private SpotifyConfiguration spotifyConfiguration;
	
	 
  
	
	
	//POST Request for creating playlist, Deserialize object and get id from parameter
	@PostMapping("/create-playlist/users/{username}/playlists")
	public ResponseEntity<String> createPlaylist(@RequestBody String nameOfPlaylist, @PathVariable String username) {
	    
		 System.out.println("Received request to create playlist");
		try {
	        // Retrieve the Users object from the repository
	        Users users = userService.findByUsername(username);

	        if (users != null) {
	            SpotifyApi spotifyApi = spotifyConfiguration.getSpotifyObject();
	            spotifyApi.setAccessToken(users.getAccessToken());
	            spotifyApi.setRefreshToken(users.getRefreshToken());

	            // Create a playlist on Spotify
	            final CreatePlaylistRequest playlistRequest = spotifyApi.createPlaylist(username, nameOfPlaylist).build();

	            // Execute the request to create a playlist
	            playlistRequest.execute();

	            return new ResponseEntity<>("Playlist has been created", HttpStatus.CREATED);
	        } else {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        // Handle exception, log or return an error response
	        return new ResponseEntity<>("Failed to create playlist: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	    
	    
	   
		
		
	
	

	
	
	
	
	
	
	
}