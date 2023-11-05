package com.proj.music.controller;
import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proj.music.repository.UserRepository;
import com.proj.music.service.UserService;
import com.proj.music.spotify.config.SpotifyConfiguration;

import jakarta.servlet.http.HttpServletResponse;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedAlbum;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.library.GetCurrentUsersSavedAlbumsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

public class SpotifyController {

	

	@Value("${custom.server.ip}")
	private String customIp;
	
	@Autowired
	private UserService userProfileService;

	@Autowired
	private SpotifyConfiguration spotifyConfiguration;
	
	
	 @CrossOrigin
	@GetMapping("login")
	public String spotifyLogin() {
		SpotifyApi object = spotifyConfiguration.getSpotifyObject();
		
		AuthorizationCodeUriRequest authorizationCodeUriRequest = object.authorizationCodeUri()
				.scope("user-library-read")
				.show_dialog(true)
				.build();
		
		final URI uri = authorizationCodeUriRequest.execute();
		return uri.toString();
	}

	 @CrossOrigin
	@GetMapping(value = "get-user-code")
	public void getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response)	throws IOException {
		SpotifyApi object = spotifyConfiguration.getSpotifyObject();
		
		AuthorizationCodeRequest authorizationCodeRequest = object.authorizationCode(userCode).build();
		User user = null;
		
		try {
			final AuthorizationCodeCredentials authorizationCode = authorizationCodeRequest.execute();

			object.setAccessToken(authorizationCode.getAccessToken());
			object.setRefreshToken(authorizationCode.getRefreshToken());
			
			final GetCurrentUsersProfileRequest getCurrentUsersProfile = object.getCurrentUsersProfile().build();
			user = getCurrentUsersProfile.execute();

			userProfileService.createUser(user, authorizationCode.getAccessToken(), authorizationCode.getRefreshToken());
		} catch (Exception e) {
			System.out.println("Exception occured while getting user code: " + e);
		}

		response.sendRedirect(customIp + "/home?id="+user.getId());
	}
	 @CrossOrigin
	@GetMapping(value = "home")
	public String home(@RequestParam String userId) {
		try {

			return userId;
		} catch (Exception e) {
			System.out.println("Exception occured while landing to home page: " + e);
		}

		return null;
	}
	
	
	
}
