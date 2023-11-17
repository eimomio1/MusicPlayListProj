package com.proj.music.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.music.entity.Reviews;
import com.proj.music.entity.Songs;
import com.proj.music.entity.Users;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.ReviewRepository;
import com.proj.music.repository.SongRepository;
import com.proj.music.repository.UserRepository;
import com.proj.music.service.ReviewService;

import jakarta.transaction.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private SongRepository songRepository;
	
	@Autowired
	private UserRepository userRepository;

//	@Override
//	public String updateReviewById(String userId, String songId,Reviews review) {
//		Optional<Reviews> review1 = reviewRepository.findReviewByUserId(userId);
//		if (review1.isPresent()) {
//			review1.get().setId(review.getId());
//			review1.get().setName(review.getName());
//			review1.get().setComment(review.getComment());
//			review1.get().setDatePosted(review.getDatePosted());
//			review1.get().setRating(review.getRating());
//		}
//		return "Review has been updated";
//	}

//	@Override
//	public String deleteReviewById(String reviewId, String songId) {
//		Optional<Reviews> review1 = reviewRepository.deleteReviewByReviewIdAndSongId(reviewId, songId);
//		if (review1.isPresent()) {
//			
//		}
//		return "Review has been deleted";
//	}

	@Override
	public Reviews getReviewById(long id) {
		return reviewRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Review Object has not been found."));
	}

	@Override
	@Transactional
	public String addReview(Reviews review, String songId, String userId) {
		Optional<Songs> optionalSong = songRepository.findSongBySpotifyId(songId);
		Optional<Users> optionalUser = Optional.of(userRepository.findByRefId(userId));
		if (optionalSong.isPresent()) {
			Reviews newReview = new Reviews();
			newReview.setSong(optionalSong.get());
			newReview.setName(review.getName());
			newReview.setRating(review.getRating());
			newReview.setUser(review.getUser());
			optionalSong.get().getReviews().add(newReview);
			optionalUser.get().getReviews().add(newReview);
			reviewRepository.save(review);
			return "Review has been added";
		} else {
			return "User not found";
		}
	}

}
