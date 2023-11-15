package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.music.entity.Reviews;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.ReviewRepository;
import com.proj.music.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

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
//	public String deleteReviewById(String userId, String songId) {
//		Optional<Reviews> review1 = reviewRepository.findReviewByUserId(userId);
//		if (review1.isPresent()) {
//			reviewRepository.deleteReviewByUserIdAndSongId(userId, songId);
//		}
//		return "Review has been deleted";
//	}

	@Override
	public Reviews getReviewById(long id) {
		return reviewRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Review Object has not been found."));
	}

	@Override
	public String addReview(Reviews review) {
		reviewRepository.save(review);
		return "Review has been added";
	}

	@Override
	public List<Reviews> getReviewsBySongId(String spotifyId) {
		return reviewRepository.findReviewBySongId(spotifyId);
	}
}
