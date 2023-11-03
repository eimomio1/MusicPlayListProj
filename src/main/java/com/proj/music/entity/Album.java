package com.proj.music.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "albums")
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "album_id")
	private int id;

	@Column(name = "album_name")
	private String name;

	@Column(name = "release_date")
	private LocalDate releaseDate;

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "ArtistsAlbum", joinColumns = @JoinColumn(name = "album_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
	private List<Artist> artist;

	public Album() {
		super();
	}

	public Album(int id, String name, LocalDate releaseDate, List<Artist> artist, byte[] images) {
		super();
		this.id = id;
		this.name = name;
		this.releaseDate = releaseDate;
		this.artist = artist;
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

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<Artist> getArtist() {
		return artist;
	}

	public void setArtist(List<Artist> artist) {
		this.artist = artist;
	}

	@Override
	public String toString() {
		return "Album [id=" + id + ", name=" + name + ", releaseDate=" + releaseDate + ", artist=" + artist + "]";
	}
}