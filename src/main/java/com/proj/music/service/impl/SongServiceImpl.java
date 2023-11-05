package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.music.entity.Song;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.SongRepository;
import com.proj.music.service.SongService;

@Service
public class SongServiceImpl implements SongService {
	
	@Autowired
	private SongRepository songRepository;

	@Override
	public String updateSongById(long id, Song song) {
		Optional<Song> song1 = songRepository.findById(id);	
		if(song1.isPresent())
		{
			song1.get().setId(song.getId());
			song1.get().setName(song.getName());
			song1.get().setReleaseDate(song.getReleaseDate());
			song1.get().setDuration(song.getDuration());
		}
		return "Song has been updated";
	}

	@Override
	public String deleteSongById(long id) {
		Optional<Song> song1 = songRepository.findById(id);
		if(song1.isPresent())
		{
			songRepository.deleteById(id);
		}
		return "Song has been deleted";
	}

	@Override
	public Song getSongById(long id) {
		return songRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Song Object has not been found."));
	}

	@Override
	public String addSong(Song song) {
		songRepository.save(song);
		return "Song has been added";
	}

	@Override
	public List<Song> getSongs() {
		return songRepository.findAll();
	}

}
