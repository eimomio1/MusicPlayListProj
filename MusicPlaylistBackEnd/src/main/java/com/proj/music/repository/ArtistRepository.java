package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Artists;

import se.michaelthelin.spotify.model_objects.specification.Artist;

public interface ArtistRepository extends JpaRepository<Artists, Long> {
	Artists findArtistBySpotifyId(String spotifyId);
	
	String deleteArtistBySpotifyId(String spotifyId);
}
