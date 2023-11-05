package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Album;

public interface AlbumService {
	String updateAlbumById(long id, Album album);

	String deleteAlbumById(long id);

	Album getAlbumById(long id);

	String addAlbum(Album album);

	List<Album> getAlbums();
}
