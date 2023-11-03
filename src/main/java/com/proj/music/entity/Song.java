package com.proj.music.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "songs")
public class Song {

	@Id
	@Column(name = "song_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "song_name")
	private String name;

	@Column(name = "duration")
	private double duration;

	@Column(name = "release_date")
	private LocalDate releaseDate;
	
	@ManyToOne
    @JoinColumn(name = "album_id") // Map the "album_id" in the Album table to create the relationship
    private Album album; // A song belongs to one album
	
	@ManyToMany
	    @JoinTable(name = "SongGenres")
	    private List<Genre> genres; // A song can be associated with multiple genres

	  @ManyToMany
	    @JoinTable(
	        name = "PlaylistSongs",
	        joinColumns = @JoinColumn(name = "song_id"),
	        inverseJoinColumns = @JoinColumn(name = "playlist_id")
	    )
	    private List<Playlist> playlists; // A song can belong to multiple playlists

	 
	  @ManyToMany(mappedBy = "songs")
	    private List<Artist> artists;  // List of artists associated with this song
	
	public Song() {
		super();
	}

	public Song(long id, String name, double duration, LocalDate releaseDate, Album album, List<Genre> genres,
			List<Playlist> playlists, List<Artist> artists) {
		super();
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.releaseDate = releaseDate;
		this.album = album;
		this.genres = genres;
		this.playlists = playlists;
		this.artists = artists;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", duration=" + duration + ", releaseDate=" + releaseDate
				+ ", album=" + album + ", genres=" + genres + ", playlists=" + playlists + ", artists=" + artists + "]";
	}

}