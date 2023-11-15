package com.proj.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proj.music.entity.Reviews;
import com.proj.music.entity.Users;
import com.proj.music.service.ReviewService;
import com.proj.music.service.SpotifyAuthService;
import com.proj.music.service.UserService;

import se.michaelthelin.spotify.SpotifyApi;

@RestController
@RequestMapping("/api")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private SpotifyAuthService spotifyService;
	
	@Autowired
	private SpotifyApi spotifyApi;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/song/{songId}/review")
	@ResponseStatus(value = HttpStatus.OK)
	public String postReviewForSong(@RequestBody Reviews review, @RequestParam String userId, @PathVariable String songId)
	{
		// first its gets the user
		Users userDetails = userService.findRefById(userId);
		if (spotifyService.isTokenExpired(userDetails.getExpiresAt())) {
			// If expired, refresh the access token
			spotifyService.refreshAccessToken(userDetails);
		}
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
		
		return reviewService.addReview(review);
	}
	
//	@DeleteMapping("/song/review/{reviewId}")
//	@ResponseStatus(value = HttpStatus.OK)
//	public String deleteReviewForSong(@RequestParam String userId, @RequestParam String songId, @PathVariable long reviewId)
//	{
//		// first its gets the user
//		Users userDetails = userService.findRefById(userId);
//		if (spotifyService.isTokenExpired(userDetails.getExpiresAt())) {
//			// If expired, refresh the access token
//			spotifyService.refreshAccessToken(userDetails);
//		}
//		// Then it pass the access token for the user to do the spotify api request
//		spotifyApi.setAccessToken(userDetails.getAccessToken());
//		// Then it refreshes the token for the user to the spotify api request
//		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
//		
//		return reviewService.deleteReviewById(userId,songId);
//	}
	
	@GetMapping("/song/review/{reviewId}")
	public Reviews getReviewByReviewIdForUser(@RequestParam String userId, @PathVariable long reviewId)
	{
		// first its gets the user
		Users userDetails = userService.findRefById(userId);
		if (spotifyService.isTokenExpired(userDetails.getExpiresAt())) {
			// If expired, refresh the access token
			spotifyService.refreshAccessToken(userDetails);
		}
		// Then it pass the access token for the user to do the spotify api request
		spotifyApi.setAccessToken(userDetails.getAccessToken());
		// Then it refreshes the token for the user to the spotify api request
		spotifyApi.setRefreshToken(userDetails.getRefreshToken());
		
		return reviewService.getReviewById(reviewId);
	}
}

