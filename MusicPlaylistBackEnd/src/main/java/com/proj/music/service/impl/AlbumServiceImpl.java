package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.music.entity.Albums;
import com.proj.music.entity.Users;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.AlbumRepository;
import com.proj.music.repository.UserRepository;
import com.proj.music.service.AlbumService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import se.michaelthelin.spotify.model_objects.specification.Album;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String updateAlbumById(long id, Albums album) {
		Optional<Albums> album1 = albumRepository.findById(id);

		if (album1.isPresent()) {
			album1.get().setId(album.getId());
			album1.get().setName(album.getName());
			album1.get().setReleaseDate(album.getReleaseDate());
			album1.get().setArtist(album.getArtist());
		}

		return "Song has been updated";
	}

	@Override
	@Transactional
	public String deleteAlbumBySpotifyId(String spotifyId) {
		List<Albums> albums = entityManager
				.createQuery("SELECT a FROM Albums a WHERE a.spotifyId = :spotifyAlbumId", Albums.class)
				.setParameter("spotifyAlbumId", spotifyId).getResultList();

		if (!albums.isEmpty()) {
			for (Albums album : albums) {
				// Disassociate from users
				List<Users> users = album.getUsers();
				users.forEach(user -> user.getAlbums().remove(album));

				// Manually delete associated records from user_albums
				Query query = entityManager.createNativeQuery("DELETE FROM user_albums WHERE album_id = :albumId");
				query.setParameter("albumId", album.getId());
				query.executeUpdate();

				// Now, delete the album
				albumRepository.delete(album);
			}
			return "Album has been deleted for user";
		} else {
			return "No album found with Spotify ID: " + spotifyId;
		}
	}

	@Override
	public Albums getAlbumById(long id) {
		return albumRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Album Object has not been found."));
	}

	@Override
	@Transactional
	public String addAlbum(Album album, String userId) {
		Optional<Users> optionalUser = Optional.of(userRepository.findByRefId(userId));

		if (optionalUser.isPresent()) {
			Users newUser = optionalUser.get();
			Albums newAlbum = new Albums();
			newAlbum.setName(album.getName());
			newAlbum.setSpotifyId(album.getId());
			newAlbum.setUri(album.getUri());
			newAlbum.setGenres(album.getGenres());
			newAlbum.setReleaseDate(album.getReleaseDate());
			newUser.getAlbums().add(newAlbum);

			albumRepository.save(newAlbum);

			return "Playlist has been added";
		} else {
			return "User not found";
		}
	}

	@Override
	public List<Albums> getAlbums() {
		return albumRepository.findAll();
	}

}
