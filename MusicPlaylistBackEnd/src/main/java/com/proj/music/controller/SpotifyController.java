package com.proj.music.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.neovisionaries.i18n.CountryCode;
import com.proj.music.entity.Users;
import com.proj.music.service.UserService;
import com.proj.music.spotify.config.SpotifyConfiguration;

import jakarta.servlet.http.HttpServletResponse;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.detailed.BadRequestException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsAlbumsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;


@CrossOrigin(origins = "http://localhost:4200") // Allow requests from your React app
@RestController
@RequestMapping("/api")
public class SpotifyController {

	@Value("${custom.server.ip}")
	private String customIp;

	@Autowired
	private UserService userProfileService;

	@Autowired
	private SpotifyConfiguration spotifyConfiguration;
	
	private String code = "";

	@GetMapping(value = "/login")
	@ResponseBody
	public ResponseEntity<Map<String, String>> spotifyLogin() {
	    SpotifyApi object = spotifyConfiguration.getSpotifyObject();
	    
	    AuthorizationCodeUriRequest authorizationCodeUriRequest = object.authorizationCodeUri()
	            .scope("user-library-read, user-read-private, user-read-email, user-top-read, playlist-modify-public")
	            .show_dialog(true)
	            .build();

	    final URI uri = authorizationCodeUriRequest.execute();
	    
	    Map<String, String> responseMap = new HashMap<>();
	    responseMap.put("url", uri.toString());
	    
	    return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	 @GetMapping("/get-user-code/")
	    public void getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response) {
	        SpotifyApi object = spotifyConfiguration.getSpotifyObject();
	        AuthorizationCodeRequest authorizationCodeRequest = object.authorizationCode(userCode).build();
	        User user = null;

	        try {
	            final AuthorizationCodeCredentials authorizationCode = authorizationCodeRequest.execute();

	            object.setAccessToken(authorizationCode.getAccessToken());
	            object.setRefreshToken(authorizationCode.getRefreshToken());

	            // Check if the access token has expired
	            if (System.currentTimeMillis() > authorizationCode.getExpiresIn()) {
	                // Refresh the access token
	                AuthorizationCodeCredentials refreshedToken = object.authorizationCodeRefresh().build().execute();
	                object.setAccessToken(refreshedToken.getAccessToken());

	                // Log refreshed access token
	                System.out.println("Refreshed Access Token: " + refreshedToken.getAccessToken());
	            }

	            final GetCurrentUsersProfileRequest getCurrentUsersProfile = object.getCurrentUsersProfile().build();
	            user = getCurrentUsersProfile.execute();

	            if (user != null && !userProfileService.userExistByRefId(user.getId())) {
	            	
	                userProfileService.createUser(user, authorizationCode.getAccessToken(), authorizationCode.getRefreshToken());
	                System.out.println("Expires in: " + authorizationCode.getExpiresIn());

	                // Construct the redirect URL with query parameters              
	                response.sendRedirect(customIp + "/home?id=" + user.getId() + "&accessToken=" + authorizationCode.getAccessToken());	              
	            } 
	            else if(user != null)
	            {
	                response.sendRedirect(customIp + "/home?id=" + user.getId() + "&accessToken=" + authorizationCode.getAccessToken());
	            }
	            else {
	                // If the user object is null, log an error message or handle it appropriately.
	                throw new RuntimeException("User object is null. The response may not contain a valid user.");
	            }
	        } catch (BadRequestException e) {
	            // Handle the exception and log an error message
	            System.out.println("Invalid authorization code: " + userCode);
	            e.printStackTrace();
	        } catch (Exception e) {
	            // Handle other exceptions
	            System.out.println("Exception occurred during Spotify authentication: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	@GetMapping(value = "/home")
	public String home(@RequestParam String userId) {
		try {
			return userId;
		}
		catch(Exception e)
		{
			System.out.println("Exception occured while landing to home page: " + e);
		}
		return null;
	}

	@GetMapping(value = "/user-top-songs")
	public Track[] getUserTopTracks(@RequestParam String userId) {
		Users userDetails = userProfileService.findRefById(userId);

		SpotifyApi object = spotifyConfiguration.getSpotifyObject();
		object.setAccessToken(userDetails.getAccessToken());
		object.setRefreshToken(userDetails.getRefreshToken());

		final GetUsersTopTracksRequest getUsersTopTracksRequest = object.getUsersTopTracks().time_range("medium_term")
				.limit(10).offset(0).build();

		try {
			final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
			return trackPaging.getItems();
		} catch (Exception e) {
			System.out.println("Exception occured while fetching top songs: " + e);
		}

		return new Track[0];
	}

	@GetMapping(value = "/artists/{id}/albums")
	public AlbumSimplified[] getArtistAlbums(@RequestParam String id) {
		Users userDetails = userProfileService.findRefById(id);

		SpotifyApi object = spotifyConfiguration.getSpotifyObject();
		object.setAccessToken(userDetails.getAccessToken());
		object.setRefreshToken(userDetails.getRefreshToken());

		final GetArtistsAlbumsRequest getArtistsAlbumRequest = object.getArtistsAlbums(id).limit(10).offset(0)
				.market(CountryCode.ES).build();

		try {
			final Paging<AlbumSimplified> trackPaging = getArtistsAlbumRequest.execute();
			return trackPaging.getItems();
		} catch (Exception e) {
			System.out.println("Exception occured while fetching top songs: " + e);
		}

		return new AlbumSimplified[0];
	}

}
