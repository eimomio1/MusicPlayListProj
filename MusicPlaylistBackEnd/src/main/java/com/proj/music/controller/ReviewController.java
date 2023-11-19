package com.proj.music.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proj.music.dto.ReviewDTO;
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

	@PostMapping("/{entityType}/{entityId}/review")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String postReview(@RequestBody Reviews review, @RequestParam String userId, @PathVariable String entityType,
			@PathVariable String entityId) {
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

		return reviewService.addReview(review, entityId, entityType, userId);
	}

	@PutMapping("/{entityType}/{entityId}/review/{reviewId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String updateReview(@RequestBody Reviews review, @PathVariable long reviewId, @PathVariable String entityId,
			@PathVariable String entityType, @RequestParam String userId) {
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

		return reviewService.updateReviewById(reviewId, entityId, entityType, review);
	}

	@DeleteMapping("/{entityType}/{entityId}/review/{reviewId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String deleteReview(@RequestParam String userId, @PathVariable String entityId,
			@PathVariable String entityType, @PathVariable long reviewId) {
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

		return reviewService.deleteReviewById(reviewId, entityType, entityId);
	}

	@GetMapping("/{entityType}/{entityId}/review/{reviewId}")
	public ResponseEntity<ReviewDTO> getReviewByReviewIdForUser(@RequestParam String userId,
			@PathVariable String entityType, @PathVariable String entityId, @PathVariable long reviewId) {
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

		ReviewDTO newReview = reviewService.getReviewById(reviewId, entityType, entityId);
		return ResponseEntity.ok().body(newReview);
	}

	@GetMapping("/{entityType}/{entityId}/reviews")
	public ResponseEntity<List<ReviewDTO>> getReviews(@RequestParam String userId, @PathVariable String entityType,
			@PathVariable String entityId) {
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

		List<ReviewDTO> reviewDTOs = reviewService.getReviews(entityType, entityId);
		// Print the reviews just before returning them

		return ResponseEntity.ok().body(reviewDTOs);

	}

	/*
	 * @GetMapping("/{entityType}/{entityId}/reviews")
	 * 
	 * @ResponseStatus(value = HttpStatus.OK) public List<Reviews>
	 * getReviews(@RequestParam String userId, @PathVariable String
	 * entityType, @PathVariable String entityId) { // first it gets the user Users
	 * userDetails = userService.findRefById(userId); if
	 * (spotifyService.isTokenExpired(userDetails.getExpiresAt())) { // If expired,
	 * refresh the access token spotifyService.refreshAccessToken(userDetails); } //
	 * Then it passes the access token for the user to do the Spotify API request
	 * spotifyApi.setAccessToken(userDetails.getAccessToken()); // Then it refreshes
	 * the token for the user to the Spotify API request
	 * spotifyApi.setRefreshToken(userDetails.getRefreshToken());
	 * 
	 * List<Reviews> reviews = reviewService.getReviews(entityType, entityId);
	 * 
	 * // Print the list of reviews for debugging System.out.println("Reviews: " +
	 * reviews);
	 * 
	 * return reviews; }
	 */

}
