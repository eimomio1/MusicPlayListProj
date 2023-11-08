package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Review;

public interface ReviewService {
	String updateReviewById(long id, Review review);

	String deleteReviewById(long id);

	Review getReviewById(long id);

	String addReview(Review review);

	List<Review> getReviews();
}
