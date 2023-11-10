package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Playlists;

import se.michaelthelin.spotify.model_objects.specification.Playlist;



public interface PlaylistService {
	String updatePlaylistById(long id, Playlists playlist);

	String deletePlaylistById(long id);

	Playlists getPlaylistById(long id);

	String addPlaylist(Playlist playlist);

	List<Playlists> getPlaylists();
}
