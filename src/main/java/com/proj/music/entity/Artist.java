package com.proj.music.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "artist")
public class Artist {

	@Id
	@Column(name = "artist_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@ManyToMany
	@JoinTable(name = "artist_song", joinColumns = @JoinColumn(name = "artist_id"), inverseJoinColumns = @JoinColumn(name = "song_id"))
	private List<Song> songs; // Artists can be associated with one or more songs.

	@ManyToMany
	@JoinTable(name = "artist_album", joinColumns = @JoinColumn(name = "artist_id"), inverseJoinColumns = @JoinColumn(name = "album_id"))
	private List<Album> albums; // Artists can be associated with multiple albums.

	@ManyToMany
	@JoinTable(name = "ArtistGenre", joinColumns = @JoinColumn(name = "artist_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private List<Genre> genres; // Artists can be associated with many genres.

	public Artist() {
		super();
	}

	public Artist(int id, String name, List<Song> songs, List<Album> albums, List<Genre> genres) {
		super();
		this.id = id;
		this.name = name;
		this.songs = songs;
		this.albums = albums;
		this.genres = genres;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + name + ", songs=" + songs + ", albums=" + albums + ", genres=" + genres
				+ "]";
	}

}