package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Songs;

public interface SongService {
	String updateSongById(long id, Songs song);

	String deleteSongById(long id);

	Songs getSongById(long id);

	String addSong(Songs song);

	List<Songs> getSongs();
}
