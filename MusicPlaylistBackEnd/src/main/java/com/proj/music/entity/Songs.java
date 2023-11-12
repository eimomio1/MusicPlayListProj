package com.proj.music.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "songs")
public class Songs {

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
	private Albums albums; // Many songs are in one album

	@ManyToMany
	@JoinTable(name = "songs_genres", joinColumns = @JoinColumn(name = "song_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private Set<Genres> genres; // A song can be associated with multiple genres

	@ManyToMany(mappedBy = "songs")
	private List<Playlists> playlists; // A song can belong to multiple playlists

	@ManyToMany(mappedBy = "songs")
	private List<Artists> artists; // List of artists associated with this song

	@OneToMany(mappedBy = "songs")
	private List<Reviews> reviews; // For one artist there are many reviews
	
	@Column(name = "uris")
	private String uris;
	
	public Songs() {
		super();
	}

	public Songs(long id, String name, double duration, LocalDate releaseDate, Albums album, Set<Genres> genres,
			List<Playlists> playlists, List<Artists> artists) {
		super();
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.releaseDate = releaseDate;
		this.albums = album;
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

	public Albums getAlbum() {
		return albums;
	}

	public void setAlbum(Albums album) {
		this.albums = album;
	}

	public Set<Genres> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genres> genres) {
		this.genres = genres;
	}

	public List<Playlists> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlists> playlists) {
		this.playlists = playlists;
	}

	public List<Artists> getArtists() {
		return artists;
	}

	public void setArtists(List<Artists> artists) {
		this.artists = artists;
	}
	
	public Albums getAlbums() {
		return albums;
	}

	public void setAlbums(Albums albums) {
		this.albums = albums;
	}

	public List<Reviews> getReviews() {
		return reviews;
	}

	public void setReviews(List<Reviews> reviews) {
		this.reviews = reviews;
	}

	public String getUris() {
		return uris;
	}

	public void setUris(String uris) {
		this.uris = uris;
	}

	@Override
	public String toString() {
		return "Songs [id=" + id + ", name=" + name + ", duration=" + duration + ", releaseDate=" + releaseDate
				+ ", albums=" + albums + ", genres=" + genres + ", playlists=" + playlists + ", artists=" + artists
				+ ", reviews=" + reviews + ", uris=" + uris + "]";
	}

}