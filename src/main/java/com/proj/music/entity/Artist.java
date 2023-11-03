package com.proj.music.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "artist")
public class Artist {

	@Column(name = "artist_id")
	private int id;

	@Column(name = "name")
	private String name;

	@ManyToMany
	@JoinTable(name = "ArtistGenre", joinColumns = @JoinColumn(name = "artist_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private List<Genre> genre;

	public Artist() {
		super();
	}

	public Artist(int id, String name, List<Genre> genre) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
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

	public List<Genre> getGenre() {
		return genre;
	}

	public void setGenre(List<Genre> genre) {
		this.genre = genre;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + name + ", genre=" + genre + "]";
	}
}