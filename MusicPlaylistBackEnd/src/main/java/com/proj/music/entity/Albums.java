package com.proj.music.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "albums")
public class Albums {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "album_id")
	private int id;

	@Column(name = "album_name")
	private String name;

	@Column(name = "release_date")
	private LocalDate releaseDate;

	// Album Entity
	@ManyToMany(mappedBy = "albums")
	private List<Artists> artist; // An album can be associated with multiple artists.

	@OneToMany
	@JoinColumn(name = "album_id") // Map the "album_id" in the Song table to create the relationship
	private List<Song> songs; // An album can contain multiple songs

	public Albums() {
		super();
	}

	public Albums(int id, String name, LocalDate releaseDate, List<Artists> artist, List<Song> songs) {
		super();
		this.id = id;
		this.name = name;
		this.releaseDate = releaseDate;
		this.artist = artist;
		this.songs = songs;

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

	public List<Artists> getArtist() {
		return artist;
	}

	public void setArtist(List<Artists> artist) {
		this.artist = artist;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "Album [id=" + id + ", name=" + name + ", releaseDate=" + releaseDate + ", artist=" + artist + ", songs="
				+ songs + "]";
	}

}