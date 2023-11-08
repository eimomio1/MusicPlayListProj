package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Artist;

public interface ArtistService {
	String updateArtistById(long id, Artist artist);

	String deleteArtistById(long id);

	Artist getArtistById(long id);

	String addArtist(Artist artist);

	List<Artist> getArtists();
}
