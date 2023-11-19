package com.proj.music.entity;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Reviews {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "comment")
	private String comment;

	@Column(name = "date_posted", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
	private LocalDateTime datePosted;

	@ManyToOne
	@JoinColumn(name = "song_id")
	private Songs songs;

	@ManyToOne
	@JoinColumn(name = "album_id")
	private Albums albums;

	@ManyToOne
	@JoinColumn(name = "playlist_id")
	private Playlists playlist;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
	
	@Column(name = "rating")
	private double rating;
	
	public Reviews() {
		super();
	}

	public Reviews(long id, String name, String comment, LocalDateTime datePosted, Songs songs, Albums albums,
			Playlists playlist, Users user, double rating) {
		super();
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.datePosted = datePosted;
		this.songs = songs;
		this.albums = albums;
		this.playlist = playlist;
		this.user = user;
		this.rating = rating;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(LocalDateTime datePosted) {
		this.datePosted = datePosted;
	}

	public Songs getSongs() {
		return songs;
	}

	public void setSongs(Songs songs) {
		this.songs = songs;
	}

	public Albums getAlbums() {
		return albums;
	}

	public void setAlbums(Albums albums) {
		this.albums = albums;
	}

	public Playlists getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlists playlist) {
		this.playlist = playlist;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
	    return "Reviews [id=" + id + ", name=" + name + ", comment=" + comment + ", datePosted=" + datePosted
	            + ", user=" + user.getId() + ", rating=" + rating + "]";
	}

}
