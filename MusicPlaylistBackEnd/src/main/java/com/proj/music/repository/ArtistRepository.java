package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Artists;

import se.michaelthelin.spotify.model_objects.specification.Artist;

public interface ArtistRepository extends JpaRepository<Artists, Long> {
	Artists findArtistByRefId(String refId);
	
	String deleteByArtistSpotifyId(String spotifyId);
	
	String addArtist(Artist Artist);
}
