package com.proj.music.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Reviews;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {
	// Method to  delete review by user_id and song_id
	void deleteReviewByUserIdAndSongId(String user_id, String song_id);
	
	// Method to find reviews by songId
	List<Reviews> findReviewBySongId(String song_id);
}
