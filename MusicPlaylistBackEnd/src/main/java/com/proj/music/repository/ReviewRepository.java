package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Reviews;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {
	// Method to  delete review by user_id and song_id
//	void deleteReviewByReviewIdAndSongId(String review_id, String song_id);
//	
//	// Method to find reviews by songId
//	List<Reviews> findReviewBySongId(String song_id);
}
