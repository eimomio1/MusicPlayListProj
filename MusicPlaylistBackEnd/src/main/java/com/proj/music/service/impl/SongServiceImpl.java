package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.music.entity.Songs;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.SongRepository;
import com.proj.music.service.SongService;

import jakarta.transaction.Transactional;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Service
public class SongServiceImpl implements SongService {

	@Autowired
	private SongRepository songRepository;

	@Override
	public String updateSongById(long id, Songs song) {
		Optional<Songs> song1 = songRepository.findById(id);
		if (song1.isPresent()) {
			song1.get().setId(song.getId());
			song1.get().setName(song.getName());
			song1.get().setReleaseDate(song.getReleaseDate());
			song1.get().setDuration(song.getDuration());
		}
		return "Song has been updated";
	}

	@Override
	public String deleteSongById(long id) {
		Optional<Songs> song1 = songRepository.findById(id);
		if (song1.isPresent()) {
			songRepository.deleteById(id);
		}
		return "Song has been deleted";
	}

	@Override
	public Songs getSongById(long id) {
		return songRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Song Object has not been found."));
	}
	
	public Songs getSongBySpotifyId(String spotifyId) {
		return songRepository.findSongBySpotifyId(spotifyId)
				.orElseThrow(() -> new ResourceNotFoundException("Song Object has not been found."));
	}

	@Override
	public String addSong(Track song) {
		Songs s1 = new Songs();
		s1.setName(song.getName());
		s1.setUris(song.getUri());
		s1.setDuration(song.getDurationMs());
		s1.setSpotifyId(song.getId());
		songRepository.save(s1);
		return "Song has been added";
	}

	@Override
	public List<Songs> getSongs() {
		return songRepository.findAll();
	}
	
	@Transactional
	public String deleteBySpotifyId(String spotifyId)
	{
		songRepository.deleteBySpotifyId(spotifyId);
		return "Song has been deleted";
	}

	@Override
	public Boolean existsBySpotifyId(String spotifyId) {
		return songRepository.existsBySpotifyId(spotifyId);
	}
}
