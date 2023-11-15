package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proj.music.entity.Playlists;
import com.proj.music.entity.Songs;
import com.proj.music.entity.Users;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.PlaylistRepository;
import com.proj.music.repository.SongRepository;
import com.proj.music.repository.UserRepository;
import com.proj.music.service.PlaylistService;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Service
public class PlaylistServiceImpl implements PlaylistService {

	@Autowired
	private PlaylistRepository playlistRepository;

	@Autowired
	private UserRepository userRespository;
	
	@Autowired
	private SongRepository songRepository;

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
	public String addPlaylist(Playlist playlist, String userId) {
		Optional<Users> optionalUser = Optional.of(userRespository.findByRefId(userId));

		if (optionalUser.isPresent()) {
			Users user = optionalUser.get();

			// Set playlist properties
			Playlists playlists = new Playlists();
			playlists.setName(playlist.getName());
			playlists.setDescription(playlist.getDescription());

             playlists.setSpotifyId(playlist.getId());

			// Add the playlist to the user's playlists
			user.getPlaylists().add(playlists);

			// Save changes
			playlistRepository.save(playlists);

			return "Playlist has been added";
		} else {
			return "User not found";
		}
	}

	@Override
	public List<Playlists> getPlaylists() {
		return playlistRepository.findAll();
	}

	@Override
	public String deletePlaylistBySpotifyId(String spotifyPlaylistId) {
		Playlists playlist1 = playlistRepository.findPlaylistBySpotifyId(spotifyPlaylistId);
		
		if(playlist1 != null) {
			
			Playlists p = playlist1;
			playlistRepository.deleteById(p.getId());
			return "Playlist has been deleted";
		}else {
			
			return "Playlist not found!";
		}
		
		
		
	}

	@Override
	public Playlists getPlaylistBySpotifyId(String spotifyPlaylistId) {
Optional<Playlists> playlist1 = Optional.of(playlistRepository.findPlaylistBySpotifyId(spotifyPlaylistId));
		
		if(playlist1.isPresent()) {
			
			Playlists plist = playlist1.get();
			
			return plist;
			
		}else {
			
			return null;
		}
		
	}

}
