package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Playlists;

public interface PlaylistRepository extends JpaRepository<Playlists, Long> {
	
//	Playlists findPlaylistById(String spotifyPlaylistId);
}
