package com.proj.music.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Reviews;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {
	
//	void deleteReviewByReviewId(long review_id, String song_id);
//	
//	// Method to find reviews by songId
//	List<Reviews> findReviewBySongId(String song_id);
    Optional<Reviews> findBySongs_SpotifyIdAndId(String songId, long reviewId);
    
    Optional<Reviews> findByPlaylist_SpotifyIdAndId(String playlistId, long reviewId);
    
    Optional<Reviews> findByAlbums_SpotifyIdAndId(String albumId, long reviewId);
    
    Optional<List<Reviews>> findBySongs_SpotifyId(String songId);
    
    Optional<List<Reviews>> findByPlaylist_SpotifyId(String playlistId);
    
    Optional<List<Reviews>> findByAlbums_SpotifyId(String albumId);
}
