package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Albums;

public interface AlbumService {
	String updateAlbumById(long id, Albums album);

	String deleteAlbumById(long id);

	Albums getAlbumById(long id);

	String addAlbum(Albums album);

	List<Albums> getAlbums();
}
