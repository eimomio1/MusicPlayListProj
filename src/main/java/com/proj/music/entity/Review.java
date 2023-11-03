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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {

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
	@JoinTable(name = "SongReviews", joinColumns = @JoinColumn(name = "review_id"), inverseJoinColumns = @JoinColumn(name = "song_id"))
	private List<Song> songs;

	@ManyToOne
	@JoinTable(name = "AlbumReviews", joinColumns = @JoinColumn(name = "review_id"), inverseJoinColumns = @JoinColumn(name = "song_id"))
	private List<Album> albums;

	@Column(name = "rating")
	private double rating;

	public Review() {
		super();
	}

	public Review(long id, String name, String comment, LocalDate datePosted, List<Song> songs, List<Album> albums,
			double rating) {
		super();
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.datePosted = datePosted;
		this.songs = songs;
		this.albums = albums;
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

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", name=" + name + ", comment=" + comment + ", datePosted=" + datePosted
				+ ", songs=" + songs + ", albums=" + albums + ", rating=" + rating + "]";
	}
}