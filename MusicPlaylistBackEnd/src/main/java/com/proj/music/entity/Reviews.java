package com.proj.music.entity;

import java.time.LocalDate;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "review_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Review") // Default type is "Review"
public class Reviews {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "comment")
	private String comment;

	@Column(name = "date_posted")
	private LocalDate datePosted;

	@ManyToOne
	@JoinColumn(name = "song_id") // many reviews for one song
	private Songs songs;

	@ManyToOne
	@JoinColumn(name = "album_id") // many reviews for one album
	private Albums albums;
	
	@ManyToOne
	@JoinColumn(name = "playlist_id") // many reviews for one playlist
	private Playlists playlist;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
	
	@Column(name = "rating")
	private double rating;
	
	public Reviews() {
		super();
	}

	public Reviews(long id, String name, String comment, LocalDate datePosted, Songs song, Albums albums, Playlists playlist,
			double rating) {
		super();
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.datePosted = datePosted;
		this.songs = song;
		this.albums = albums;
		this.playlist = playlist;
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

	public LocalDate getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(LocalDate datePosted) {
		this.datePosted = datePosted;
	}

	public Songs getSong() {
		return songs;
	}

	public void setSong(Songs song) {
		this.songs = song;
	}

	public Albums getAlbum() {
		return albums;
	}

	public void setAlbum(Albums albums) {
		this.albums = albums;
	}

	public Playlists getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlists playlist) {
		this.playlist = playlist;
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
				+ ", song=" + songs + ", album=" + albums + ", playlist=" + playlist + ", rating=" + rating + "]";
	}

}
