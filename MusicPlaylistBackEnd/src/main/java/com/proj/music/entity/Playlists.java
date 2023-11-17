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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlist")
public class Playlists {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "playlist_id")
	private long id;

	@Column(name = "playlist_name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "spotifyId")
	private String spotifyId;

	@ManyToMany
	@JoinTable(name = "playlist_songs", joinColumns = @JoinColumn(name = "playlist_id"), inverseJoinColumns = @JoinColumn(name = "song_id"))
	private List<Songs> songs; // A playlist can contain multiple songs

	@Column(name = "createdAt")
	private LocalDate createdAt;

	@Column(name = "updatedAt")
	private LocalDate updatedAt;

	@ManyToMany(mappedBy = "playlists")
	private List<Users> users; // A playlist can be associated with multiple users

	@OneToMany(mappedBy = "playlist")
	private List<Reviews> reviews; // For one playlist there are many reviews

	public Playlists() {
		super();
	}

	public Playlists(long id, String name, String description, String spotifyId, List<Songs> songs, LocalDate createdAt,
			LocalDate updatedAt, List<Users> users, List<Reviews> reviews) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.spotifyId = spotifyId;
		this.songs = songs;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.users = users;
		this.reviews = reviews;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpotifyId() {
		return spotifyId;
	}

	public void setSpotifyId(String spotifyId) {
		this.spotifyId = spotifyId;
	}

	public List<Songs> getSongs() {
		return songs;
	}

	public void setSongs(List<Songs> songs) {
		this.songs = songs;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public List<Reviews> getReviews() {
		return reviews;
	}

	public void setReviews(List<Reviews> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "Playlists [id=" + id + ", name=" + name + ", description=" + description + ", spotifyId=" + spotifyId
				+ ", songs=" + songs + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", users=" + users
				+ ", reviews=" + reviews + "]";
	}

}