package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Artists;

public interface ArtistService {
	String updateArtistById(long id, Artists artist);

	String deleteArtistById(long id);

	Artists getArtistById(long id);

	String addArtist(Artists artist);

	List<Artists> getArtists();
	
	Artists findArtistByRefId(String id);
}
