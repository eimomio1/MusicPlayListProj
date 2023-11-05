package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.proj.music.entity.Review;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.ReviewRepository;
import com.proj.music.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	private ReviewRepository reviewRepository;
	
	@Override
	public String updateReviewById(long id, Review review) {
		Optional<Review> review1 = reviewRepository.findById(id);	
		if(review1.isPresent())
		{
			review1.get().setId(review.getId());
			review1.get().setName(review.getName());
			review1.get().setComment(review.getComment());
			review1.get().setDatePosted(review.getDatePosted());
			review1.get().setRating(review.getRating());
		}
		return "Review has been updated";
	}

	@Override
	public String deleteReviewById(long id) {
		Optional<Review> review1 = reviewRepository.findById(id);
		if(review1.isPresent())
		{
			reviewRepository.deleteById(id);
		}
		return "Review has been deleted";
	}

	@Override
	public Review getReviewById(long id) {
		return reviewRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Review Object has not been found."));
	}

	@Override
	public String addReview(Review review) {
		reviewRepository.save(review);
		return "Review has been added";
	}

	@Override
	public List<Review> getReviews() {
		return reviewRepository.findAll();
	}

}
