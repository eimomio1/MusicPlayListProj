package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Song;

public interface SongService {
	String updateSongById(long id, Song song);

	String deleteSongById(long id);

	Song getSongById(long id);

	String addSong(Song song);

	List<Song> getSongs();
}
