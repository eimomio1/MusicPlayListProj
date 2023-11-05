package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Playlist;

public interface PlaylistService {
	String updatePlaylistById(long id, Playlist playlist);

	String deletePlaylistById(long id);

	Playlist getPlaylistById(long id);

	String addPlaylist(Playlist playlist);

	List<Playlist> getPlaylists();
}
