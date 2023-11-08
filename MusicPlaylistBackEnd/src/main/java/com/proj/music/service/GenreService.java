package com.proj.music.service;

import java.util.List;

import com.proj.music.entity.Genre;

public interface GenreService {
	String updateGenreById(long id, Genre genre);

	String deleteGenreById(long id);

	Genre getGenreById(long id);

	String addGenre(Genre genre);

	List<Genre> getGenres();
}
