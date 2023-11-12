package com.proj.music.entity;

import java.sql.Date;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "albums")
public class Albums {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "album_id")
	private int id;

	@Column(name = "album_name")
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "release_date")
	private Date releaseDate;

	@ManyToMany(mappedBy = "albums")
	private List<Artists> artist; // An album can be associated with multiple artists.

	@OneToMany(mappedBy = "albums")
	private List<Songs> songs; // for one album it contains multiple songs

	@OneToMany(mappedBy = "albums")
	private List<Reviews> reviews; // For one album there are many reviews
	
	public Albums() {
		super();
	}

	public Albums(int id, String name, Date releaseDate, List<Artists> artist, List<Songs> songs) {
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

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<Artists> getArtist() {
		return artist;
	}

	public void setArtist(List<Artists> artist) {
		this.artist = artist;
	}

	public List<Songs> getSongs() {
		return songs;
	}

	public void setSongs(List<Songs> songs) {
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "Albums [id=" + id + ", name=" + name + ", releaseDate=" + releaseDate + ", artist=" + artist
				+ ", songs=" + songs + ", reviews=" + reviews + "]";
	}

}