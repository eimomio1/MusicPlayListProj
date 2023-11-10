package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.proj.music.entity.Artist;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.ArtistRepository;
import com.proj.music.service.ArtistService;

public class ArtistServiceImpl implements ArtistService {

	@Autowired
	private ArtistRepository artistRepository;

	@Override
	public String updateArtistById(long id, Artist artist) {

		Optional<Artist> artist1 = artistRepository.findById(id);

		if (artist1.isPresent()) {
			artist1.get().setId(artist.getId());
			artist1.get().setName(artist.getName());
			artist1.get().setGenres(artist.getGenres());
			artist1.get().setSongs(artist.getSongs());
			artist1.get().setAlbums(artist.getAlbums());
		}

		return "Artist has been updated";
	}

	@Override
	public String deleteArtistById(long id) {
		Optional<Artist> artist1 = artistRepository.findById(id);

		if (artist1.isPresent()) {
			artistRepository.deleteById(id);
		}

		return "Artist has been deleted";
	}

	@Override
	public Artist getArtistById(long id) {
		return artistRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Artist has not been found"));
	}

	@Override
	public String addArtist(Artist artist) {
		artistRepository.save(artist);
		return "Artist has been added";
	}

	@Override
	public List<Artist> getArtists() {
		return artistRepository.findAll();
	}

}
