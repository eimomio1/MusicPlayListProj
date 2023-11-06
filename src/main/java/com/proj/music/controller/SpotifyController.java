package com.proj.music.controller;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.neovisionaries.i18n.CountryCode;
import com.proj.music.entity.Users;
import com.proj.music.repository.UserRepository;
import com.proj.music.service.UserService;
import com.proj.music.spotify.config.SpotifyConfiguration;

import jakarta.servlet.http.HttpServletResponse;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedAlbum;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsAlbumsRequest;
import se.michaelthelin.spotify.requests.data.library.GetCurrentUsersSavedAlbumsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

@CrossOrigin(origins = "http://localhost:3000") // Allow requests from your React app
@RestController
@RequestMapping("/api")
public class SpotifyController {

	@Value("${custom.server.ip}")
	private String customIp;

	@Autowired
	private UserService userProfileService;

	@Autowired
	private SpotifyConfiguration spotifyConfiguration;

	@GetMapping(value = "/login")
	@ResponseBody
	public String spotifyLogin() {
		SpotifyApi object = spotifyConfiguration.getSpotifyObject();

		AuthorizationCodeUriRequest authorizationCodeUriRequest = object.authorizationCodeUri()
				.scope("user-library-read, user-read-private, user-read-email, user-top-read").show_dialog(true).build();

		final URI uri = authorizationCodeUriRequest.execute();
		return uri.toString();
	}

	@GetMapping(value = "/get-user-code/")
    public String getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException {
        SpotifyApi object = spotifyConfiguration.getSpotifyObject();
        AuthorizationCodeRequest authorizationCodeRequest = object.authorizationCode(userCode).build();
//        User user = null;

        try {
            final AuthorizationCodeCredentials authorizationCode = authorizationCodeRequest.execute();
            object.setAccessToken(authorizationCode.getAccessToken());
            object.setRefreshToken(authorizationCode.getRefreshToken());

//            final GetCurrentUsersProfileRequest getCurrentUsersProfile = object.getCurrentUsersProfile().build();
//            user = getCurrentUsersProfile.execute();

//            if (user != null) {
//                userProfileService.createUser(user, authorizationCode.getAccessToken(), authorizationCode.getRefreshToken());
// 
//            } else {
//                // If the user object is null, log an error message or handle it appropriately.
//                System.out.println("User object is null. The response may not contain a valid user.");
//                // You can also log more details about the response if needed.
//            }
        System.out.println("Expries in: " + authorizationCode.getExpiresIn());
        } catch (Exception e) {
            // Handle exceptions that may occur during the request.
            System.out.println("Exception occurred while getting user profile: " + e.getMessage());
            e.printStackTrace(); // Print the exception details for debugging.
        }
        response.sendRedirect("http://localhost:3000/top-artists");
        return object.getAccessToken();
    }

	@GetMapping(value = "/home")
	public String home(@RequestParam String userId) {
		try {

			return userId;
		} catch (Exception e) {
			System.out.println("Exception occured while landing to home page: " + e);
		}

		return null;
	}
	
	@GetMapping(value = "/user-top-artists")
	public Artist[] getUserTopArtists() {
		SpotifyApi object = spotifyConfiguration.getSpotifyObject();
		final GetUsersTopArtistsRequest getUsersTopArtistsRequest = object.getUsersTopArtists()
				.time_range("medium_term")
				.limit(10)
				.offset(5)
				.build();
		try {
			final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
			return artistPaging.getItems();
		} catch(Exception e) {
			System.out.println("Something went wrong!\n" + e.getMessage());
		}
		return new Artist[0];
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
