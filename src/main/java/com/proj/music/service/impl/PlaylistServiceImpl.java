package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import com.proj.music.entity.Playlist;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.PlaylistRepository;
import com.proj.music.service.PlaylistService;

public class PlaylistServiceImpl implements PlaylistService {
	
	private PlaylistRepository playlistRepository;
	
	@Override
	public String updatePlaylistById(long id, Playlist playlist) {
		Optional<Playlist> playlist1 = playlistRepository.findById(id);	
		if(playlist1.isPresent())
		{
			playlist1.get().setId(playlist.getId());
			playlist1.get().setName(playlist.getName());
			playlist1.get().setSongs(playlist.getSongs());
			playlist1.get().setUpdatedAt(playlist.getUpdatedAt());
			playlist1.get().setDescription(playlist.getDescription());
			playlist1.get().setCreatedAt(playlist.getCreatedAt());
		}
		return "Playlist has been updated";
	}

	@Override
	public String deletePlaylistById(long id) {
		Optional<Playlist> playlist1 = playlistRepository.findById(id);
		if(playlist1.isPresent())
		{
			playlistRepository.deleteById(id);
		}
		return "Playlist has been deleted";
	}

	@Override
	public Playlist getPlaylistById(long id) {
		return playlistRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Playlist Object has not been found."));
	}

	@Override
	public String addPlaylist(Playlist playlist) {
		playlistRepository.save(playlist);
		return "Playlist has been added";
	}

	@Override
	public List<Playlist> getPlaylists() {
		return playlistRepository.findAll();
	}

}
