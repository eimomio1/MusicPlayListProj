package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.proj.music.entity.Album;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.AlbumRepository;
import com.proj.music.service.AlbumService;

public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumRepository albumRepository;

	@Override
	public String updateAlbumById(long id, Album album) {
		Optional<Album> album1 = albumRepository.findById(id);

		if (album1.isPresent()) {
			album1.get().setId(album.getId());
			album1.get().setName(album.getName());
			album1.get().setReleaseDate(album.getReleaseDate());
			album1.get().setArtist(album.getArtist());
		}

		return "Song has been updated";
	}

	@Override
	public String deleteAlbumById(long id) {
		Optional<Album> album1 = albumRepository.findById(id);

		if (album1.isPresent()) {
			albumRepository.deleteById(id);
		}

		return "Album has been deleted";
	}

	@Override
	public Album getAlbumById(long id) {
		return albumRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Album Object has not been found."));
	}

	@Override
	public String addAlbum(Album album) {
		albumRepository.save(album);
		return "Album has been added";
	}

	@Override
	public List<Album> getAlbums() {
		return albumRepository.findAll();
	}

}
