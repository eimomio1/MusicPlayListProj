package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.music.entity.Songs;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.SongRepository;
import com.proj.music.service.SongService;

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

	@Override
	public String addSong(Songs song) {
		songRepository.save(song);
		return "Song has been added";
	}

	@Override
	public List<Songs> getSongs() {
		return songRepository.findAll();
	}

}
