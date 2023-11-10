package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.music.entity.Playlists;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.PlaylistRepository;
import com.proj.music.service.PlaylistService;

import se.michaelthelin.spotify.model_objects.specification.Playlist;

@Service
public class PlaylistServiceImpl implements PlaylistService {

	@Autowired
	private PlaylistRepository playlistRepository;

	@Override
	public String updatePlaylistById(long id, Playlists playlist) {
		Optional<Playlists> playlist1 = playlistRepository.findById(id);
		if (playlist1.isPresent()) {
			playlist1.get().setId(playlist.getId());
			playlist1.get().setName(playlist.getName());
			playlist1.get().setDescription(playlist.getDescription());
		}
		return "Playlist has been updated";
	}

	@Override
	public String deletePlaylistById(long id) {
		Optional<Playlists> playlist1 = playlistRepository.findById(id);
		if (playlist1.isPresent()) {
			playlistRepository.deleteById(id);
		}
		return "Playlist has been deleted";
	}

	@Override
	public Playlists getPlaylistById(long id) {
		return playlistRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Playlist Object has not been found."));
	}

	@Override
	public String addPlaylist(Playlist playlist) {
		Playlists playlists = new Playlists();
		playlists.setName(playlist.getName());
		playlists.setDescription(playlist.getDescription());
		playlistRepository.save(playlists);
		return "Playlist has been added";
	}

	@Override
	public List<Playlists> getPlaylists() {
		return playlistRepository.findAll();
	}

}
