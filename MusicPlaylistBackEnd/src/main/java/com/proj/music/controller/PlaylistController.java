package com.proj.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.proj.music.entity.Users;
import com.proj.music.service.PlaylistService;
import com.proj.music.service.SpotifyAuthService;
import com.proj.music.service.UserService;
import com.proj.music.spotify.config.SpotifyConfiguration;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.BadRequestException;
import se.michaelthelin.spotify.model_objects.special.FeaturedPlaylists;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.requests.data.browse.GetCategorysPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.browse.GetListOfFeaturedPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistCoverImageRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.playlists.UploadCustomPlaylistCoverImageRequest;

import java.io.IOException;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200") // Add your frontend URL
public class PlaylistController {

	private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class);

	@Autowired
	UserService userService;

	@Autowired
	PlaylistService playlistService;
	
	@Autowired
	SpotifyAuthService spotifyService;

	@Autowired
	private SpotifyConfiguration spotifyConfiguration;

	@Autowired
	private SpotifyApi spotifyApi;

	@PostMapping("/create-playlist/users/{userId}/playlists")
	public ResponseEntity<String> createPlaylist(@RequestBody String nameOfPlaylist, @PathVariable String userId) {
		System.out.println("Received request to create playlist");

	    try {
	        // Retrieve the Users object from the repository
	        Users users = userService.findRefById(userId);

	        if (users != null) {
	            // Check if the access token is still valid
	            if (spotifyService.isTokenExpired(users.getExpiresAt())) {
	                // If expired, refresh the access token
	                spotifyService.refreshAccessToken(users);
	            }

	            spotifyApi.setAccessToken(users.getAccessToken());
	            spotifyApi.setRefreshToken(users.getRefreshToken());

	            // Create a playlist on Spotify
	            final CreatePlaylistRequest.Builder playlistBuilder = spotifyApi.createPlaylist(userId, nameOfPlaylist);

	            // Log the request payload
	            logger.info("Playlist Request Payload: {}", playlistBuilder.build().getBody());

	            final CreatePlaylistRequest playlistRequest = playlistBuilder.build();

	            Playlist newPlaylist = playlistRequest.execute();
	            // Saves playlist to database table
	            playlistService.addPlaylist(newPlaylist, userId);

	            return new ResponseEntity<>("Playlist has been created", HttpStatus.CREATED);
	        } else {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (BadRequestException e) {
	        // Log the detailed error information
	        logger.error("Error creating playlist: {}", e.getMessage());
	        e.printStackTrace(); // This will print the stack trace for more details
	        return new ResponseEntity<>("Failed to create playlist: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    } catch (Exception e) {
	        logger.error("Failed to create playlist", e);
	        // Handle exception, log, or return an error response
	        return new ResponseEntity<>("Failed to create playlist: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	// Get album By Id
	@GetMapping(value = "/playlists/{playlistId}")
	public ResponseEntity<Playlist> getPlaylistById(@PathVariable String playlistId, @RequestParam String userId)
			throws ParseException, SpotifyWebApiException, IOException {
		// first its gets the user
		Users userDetails = userService.findRefById(userId);
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
		// Then it gets the album
		final GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist(playlistId).build();
		// creates a album object
		Playlist playlist = getPlaylistRequest.execute();
		// returns a http response back with album being created and converted into
		// json
		return new ResponseEntity<Playlist>(playlist, HttpStatus.OK);
	}

	// Get album By Id
	@GetMapping(value = "/playlists/{playlistId}/tracks")
	@ResponseStatus(value = HttpStatus.OK)
	public PlaylistTrack[] getPlaylistItems(@PathVariable String playlistId, @RequestParam String userId)
			throws ParseException, SpotifyWebApiException, IOException {
		// first its gets the user
		Users userDetails = userService.findRefById(userId);
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
		// Then it gets the album
		final GetPlaylistsItemsRequest getPlaylistItemsRequest = spotifyApi.getPlaylistsItems(playlistId).build();
		try {
			// creates a album object
			Paging<PlaylistTrack> playlist = getPlaylistItemsRequest.execute();
			// returns a http response back with album being created and converted into
			return playlist.getItems();
		} catch (Exception e) {
			System.out.println("Exception occured while fetching: " + e);
		}
		return new PlaylistTrack[0];
	}

	// Get album By Id
	@GetMapping(value = "/me/playlists")
	@ResponseStatus(value = HttpStatus.OK)
	public PlaylistSimplified[] getCurrUsersPlaylist(@RequestParam String userId)
			throws ParseException, SpotifyWebApiException, IOException {
		// first its gets the user
		Users userDetails = userService.findRefById(userId);
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
		// Then it gets the playlist for users
		final GetListOfCurrentUsersPlaylistsRequest getCurrUserPlaylistRequest = spotifyApi
				.getListOfCurrentUsersPlaylists().build();
		try {
			// creates a playlistSimplified object
			Paging<PlaylistSimplified> playlist = getCurrUserPlaylistRequest.execute();
			// returns a http response back with playlist being created and converted into
			return playlist.getItems();
		} catch (Exception e) {
			System.out.println("Exception occured while fetching: " + e);
		}
		return new PlaylistSimplified[0];
	}

	// Get users playlist
	@GetMapping(value = "/users/{userId}/playlists")
	@ResponseStatus(value = HttpStatus.OK)
	public PlaylistSimplified[] getUserPlaylist(@PathVariable String userId)
			throws ParseException, SpotifyWebApiException, IOException {
		// first its gets the user
		Users userDetails = userService.findRefById(userId);
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
		// Then it gets the playlist for users
		final GetListOfUsersPlaylistsRequest getListOfUserPlaylistRequest = spotifyApi.getListOfUsersPlaylists(userId)
				.build();
		try {
			// creates a playlistSimplified object
			Paging<PlaylistSimplified> playlist = getListOfUserPlaylistRequest.execute();
			// returns a http response back with playlist being created and converted into
			return playlist.getItems();
		} catch (Exception e) {
			System.out.println("Exception occured while fetching: " + e);
		}
		return new PlaylistSimplified[0];
	}

	// Get Feature playlist
	@GetMapping(value = "/browse/featured-playlist")
	@ResponseStatus(value = HttpStatus.OK)
	public FeaturedPlaylists getFeaturePlaylist(@RequestParam String userId)
			throws ParseException, SpotifyWebApiException, IOException {
		// first its gets the user
		Users userDetails = userService.findRefById(userId);
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
		// Then it send an accesstoken to spotify to get list of featured playlist;i0
		final GetListOfFeaturedPlaylistsRequest getListOfFeaturedRequest = spotifyApi.getListOfFeaturedPlaylists()
				.build();
		// creates a featuedplaylist object
		FeaturedPlaylists featuredPlaylist = getListOfFeaturedRequest.execute();
		// returns a http response back with featureplalyist
		return featuredPlaylist;
	}

	// Get Feature playlist
	@GetMapping(value = "/browse/categories/{categoryId}/playlists")
	@ResponseStatus(value = HttpStatus.OK)
	public PlaylistSimplified[] getCategoryPlaylist(@RequestParam String userId, @PathVariable String categoryId)
			throws ParseException, SpotifyWebApiException, IOException {
		// first its gets the users
		Users userDetails = userService.findRefById(userId);
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
		// Then it send an accesstoken to spotify to get list of featured playlist;i0
		final GetCategorysPlaylistsRequest getCategoryPlaylistsRequest = spotifyApi.getCategorysPlaylists(categoryId)
				.build();
		// creates a featuedplaylist object
		Paging<PlaylistSimplified> categoryPlaylist = getCategoryPlaylistsRequest.execute();
		// returns a http response back with featureplalyist
		return categoryPlaylist.getItems();
	}

	// Get Feature playlist
	@GetMapping(value = "/playlists/{playlistId}/images")
	@ResponseStatus(value = HttpStatus.OK)
	public Image[] getPlaylistCoverImage(@PathVariable String playlistId, @RequestParam String userId)
			throws ParseException, SpotifyWebApiException, IOException {
		// first its gets the users
		Users userDetails = userService.findRefById(userId);
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
		// Then it send an accesstoken to spotify to get list of featured playlist;i0
		final GetPlaylistCoverImageRequest getPlaylistsCoverImageRequest = spotifyApi.getPlaylistCoverImage(playlistId)
				.build();
		// creates a featuedplaylist object
		Image[] playlistCoverImage = getPlaylistsCoverImageRequest.execute();
		// returns a http response back with featureplalyist
		return playlistCoverImage;
	}
//
//	@PostMapping(value = "/playlists/{playlistId}/songs")
//	@ResponseStatus(value = HttpStatus.CREATED)
//	public SnapshotResult addSongsToPlaylist(@PathVariable String playlistId, @RequestParam String userId,
//			@RequestParam String... songUri) throws ParseException, SpotifyWebApiException, IOException {
//		// first its gets the users
//		Users userDetails = userService.findRefById(userId);
//		// Then it pass the access token for the user to do the spotify api request
//		spotifyApi.setAccessToken(userDetails.getAccessToken());
//		// Then it refreshes the token for the user to the spotify api request
//		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
//		// It send access token to spotify to add songs to playlist
//		
//		playlistService.addSongToPlaylist(null, songUri);
//		
//		final AddItemsToPlaylistRequest addSongsPlaylistRequest = spotifyApi.addItemsToPlaylist(playlistId, songUri)
//				.build();
//		
//		SnapshotResult songSavedToPlaylist = addSongsPlaylistRequest.execute();
//		return songSavedToPlaylist;
//	}

	// Deletes Songs From Playlist
	@DeleteMapping(value = "/playlists/{playlistId}/songs")
	@ResponseStatus(value = HttpStatus.OK)
	public String removePlaylistSongs(@PathVariable String playlistId, @RequestParam String userId,
			@RequestParam String... songUri) {
		// first its gets the users
		Users userDetails = userService.findRefById(userId);
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());

//		final RemoveItemsFromPlaylistRequest removeItemsFromPlaylist = spotifyApi.remove

		return "Playlist has been deleted";
	}

	@PutMapping(value = "/playlists/{playlistId}/images")
	@ResponseStatus(value = HttpStatus.OK)
	public String updateCustomImagePlaylist(@RequestParam String userId, @PathVariable String playlistId) throws ParseException, SpotifyWebApiException, IOException {
		// first its gets the users
		Users userDetails = userService.findRefById(userId);
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
		// It send access token to spotify for the request to update playlist customer image 
		final UploadCustomPlaylistCoverImageRequest uploadCustomerPlaylistCoverImageRqst = spotifyApi
				.uploadCustomPlaylistCoverImage(playlistId).build();
		String uploadPlaylistCoverImage = uploadCustomerPlaylistCoverImageRqst.execute();
		
		return uploadPlaylistCoverImage;
	}
}