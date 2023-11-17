package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Reviews;

public interface ReviewService {
//	String updateReviewById(String userId, String songId, Reviews reviews);

//	String deleteReviewById(String userId, String songId);

	Reviews getReviewById(long id);

	String addReview(Reviews review, String songId, String userId);

//	List<Reviews> getReviewsBySongId(String spotifyId);
}
