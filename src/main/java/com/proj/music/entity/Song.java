package com.proj.music.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Song {
	@Id
	@Column(name = "song_id", nullable = false)
	private int id;
	@Column(name ="name")
	private String name;
	@Column(name = "duration")
	private double duration;
	@Column(name = "releaseDate")
	private LocalDate releaseDate;
	public Song() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Song(int id, String name, double duration, LocalDate releaseDate) {
		super();
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.releaseDate = releaseDate;
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
	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", duration=" + duration + ", releaseDate=" + releaseDate + "]";
	}
}
