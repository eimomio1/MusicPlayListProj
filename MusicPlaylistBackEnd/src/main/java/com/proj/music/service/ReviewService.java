package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Reviews;

public interface ReviewService {
	String updateReviewById(long id, Reviews review);

	String deleteReviewById(long id);

	Reviews getReviewById(long id);

	String addReview(Reviews review);

	List<Reviews> getReviews();
}
