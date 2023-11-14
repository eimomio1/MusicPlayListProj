package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Songs;

public interface SongRepository extends JpaRepository<Songs, Long> {
	void deleteBySpotifyId(String spotifyId);
	
	Boolean existsBySpotifyId(String spotifyId);
}
