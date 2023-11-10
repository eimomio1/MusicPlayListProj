package com.proj.music.controller;

import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neovisionaries.i18n.CountryCode;
import com.proj.music.entity.Artists;
import com.proj.music.entity.Users;
import com.proj.music.service.ArtistService;
import com.proj.music.service.UserService;
import com.proj.music.spotify.config.SpotifyConfiguration;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsAlbumsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;

@RestController
@RequestMapping("/api")
public class ArtistController {

	@Autowired
	private UserService userProfileService;

	@Autowired
	private SpotifyConfiguration spotifyConfiguration;

	@Autowired
	private ArtistService artistService;

	private final SpotifyApi spotifyApi;

	@Autowired
	public ArtistController(SpotifyApi spotifyApi) {
		this.spotifyApi = spotifyApi;
	}

	@GetMapping(value = "/artists/{id}")
	public ResponseEntity<Artist> getArtistById(@PathVariable String id, @RequestParam String userId)
			throws ParseException, SpotifyWebApiException, IOException {
		// first its gets the user
		Users userDetails = userProfileService.findRefById(userId);
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
		// Then it gets the artist
		final GetArtistRequest getArtistRequest = spotifyApi.getArtist(id).build();
		// creates a artist object
		Artist artist = getArtistRequest.execute();
		// returns a http response back with artist being created and converted into
		// json
		return new ResponseEntity<Artist>(artist, HttpStatus.OK);
	}

	@GetMapping(value = "/user-top-artists")
	public Artist[] getUserTopArtists(@RequestParam String userId) {
		Users userDetails = userProfileService.findRefById(userId);

		spotifyApi.setAccessToken(userDetails.getAccessToken());

		spotifyApi.setRefreshToken(userDetails.getRefreshToken());

		final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
				.time_range("medium_term").limit(10).offset(0).build();
		try {
			final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
			return artistPaging.getItems();
		} catch (Exception e) {
			System.out.println("Something went wrong!\n" + e.getMessage());
		}
		return new Artist[0];
	}

//	@GetMapping(value = "/artists/{id}/albums")
//	public ResponseEntity<List<Album>> getArtistAlbums(String artistId, @RequestParam String userId) {
//		// first its gets the user
//		Users userDetails = userProfileService.findRefById(userId);
//		// Then it pass the access token for the user to do the spotify api request
//		spotifyApi.setAccessToken(userDetails.getAccessToken());
//		// Then it refreshes the token for the user to the spotify api request
//		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
//		// Then it gets the artist albums
//		final GetArtistsAlbumsRequest getArtistsAlbumRequest = spotifyApi.getArtistsAlbums(artistId)
//				.market(CountryCode.ES).limit(10).offset(1).build();
//		try {
//			// put albums in pages after the 10 albums
//			final Paging<AlbumSimplified> albumPaging = getArtistsAlbumRequest.execute();
//			return new ResponseEntity<List<Album>>(List.of(albumPaging.getItems()), HttpStatus.OK);
//		}
//		catch(Exception e)
//		{
//			System.out.println("Something went wrong!\n" + e.getMessage());
//		}
//	}
}
