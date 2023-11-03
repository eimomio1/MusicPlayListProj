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
@Table(name = "review_tbl")
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
    @JoinTable(name = "SongReviews",
    joinColumns = @JoinColumn(name = "review_id"),
    inverseJoinColumns = @JoinColumn(name = "song_id"))
	private List<Song> songs;
	@ManyToOne
    @JoinTable(name = "AlbumReviews",
    joinColumns = @JoinColumn(name = "review_id"),
    inverseJoinColumns = @JoinColumn(name = "song_id"))
	private List<Album> albums;
	@Column(name = "rating")
	private double rating;
}