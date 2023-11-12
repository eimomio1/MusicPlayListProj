package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Songs;

import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;

public interface SongRepository extends JpaRepository<Songs, Long> {
	Track getSongByUris(String uris);
}
