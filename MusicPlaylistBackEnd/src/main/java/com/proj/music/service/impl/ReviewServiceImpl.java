package com.proj.music.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.music.entity.Albums;
import com.proj.music.entity.Playlists;
import com.proj.music.entity.Reviews;
import com.proj.music.entity.Songs;
import com.proj.music.entity.Users;
import com.proj.music.exceptions.ResourceNotFoundException;
import com.proj.music.repository.AlbumRepository;
import com.proj.music.repository.PlaylistRepository;
import com.proj.music.repository.ReviewRepository;
import com.proj.music.repository.SongRepository;
import com.proj.music.repository.UserRepository;
import com.proj.music.service.ReviewService;

import jakarta.transaction.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private SongRepository songRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PlaylistRepository playlistRepository;

	@Autowired
	private AlbumRepository albumRepository;

	@Override
	public String updateReviewById(long reviewId, String entityId, String entityType, Reviews review) {
		Optional<Reviews> review1 = null;
		switch (entityType) {
		case "songs":
				review1 = reviewRepository.findBySongs_SpotifyIdAndId(entityId, reviewId);
				review1.get().setName(review.getName());
				review1.get().setComment(review.getComment());
				review1.get().setDatePosted(review.getDatePosted());
				review1.get().setRating(review.getRating());
				reviewRepository.save(review1.get());
			break;

		case "albums":
				review1 = reviewRepository.findByAlbums_SpotifyIdAndId(entityId, reviewId);
				review1.get().setName(review.getName());
				review1.get().setComment(review.getComment());
				review1.get().setDatePosted(review.getDatePosted());
				review1.get().setRating(review.getRating());
				reviewRepository.save(review1.get());
			break;

		case "playlist":
				review1 = reviewRepository.findByPlaylist_SpotifyIdAndId(entityId, reviewId);
				review1.get().setName(review.getName());
				review1.get().setComment(review.getComment());
				review1.get().setDatePosted(review.getDatePosted());
				review1.get().setRating(review.getRating());
				reviewRepository.save(review1.get());			
			break;

		default:
			throw new ResourceNotFoundException("Object has not been found");
		}
		return "Review for " + entityType + " has been updated";
	}

	@Override
	public String deleteReviewById(long reviewId, String entityType, String entityId) {
		Optional<Reviews> review1 = null;
		String reviewType = "";
		switch (entityType) {
		case "songs":
			review1 = reviewRepository.findBySongs_SpotifyIdAndId(entityId, reviewId);
			reviewType = "songs";
			reviewRepository.delete(review1.get());
			break;

		case "playlist":
			review1 = reviewRepository.findByPlaylist_SpotifyIdAndId(entityId, reviewId);
			reviewType = "playlist";
			reviewRepository.delete(review1.get());
			break;

		case "albums":
			review1 = reviewRepository.findByAlbums_SpotifyIdAndId(entityId, reviewId);
			reviewType = "albums";
			reviewRepository.delete(review1.get());
			break;

		default:
			System.out.println("Object could not be found to be deleted");
			throw new ResourceNotFoundException("Object could not be found");
		}
		return "Review has been deleted For " + reviewType;
	}

	@Override
	public Reviews getReviewById(long reviewid, String entityType, String entityId) {
		Optional<Reviews> foundReview = null;
		switch (entityType) {
		case "songs":
			foundReview = reviewRepository.findBySongs_SpotifyIdAndId(entityId, reviewid);
			break;
		case "playlist":
			foundReview = reviewRepository.findByPlaylist_SpotifyIdAndId(entityId, reviewid);
			break;
		case "albums":
			foundReview = reviewRepository.findByAlbums_SpotifyIdAndId(entityId, reviewid);
			break;
		default:
			System.out.println("Object has not been found.");
			break;
		}
		return foundReview.orElseThrow(()-> new ResourceNotFoundException("Object has not been found"));
	}
	
	@Override
	public List<Reviews> getReviews(String entityType, String entityId) {
		Optional<List<Reviews>> foundReview = null;
		switch (entityType) {
		case "songs":
			foundReview = reviewRepository.findBySongs_SpotifyId(entityId);
			break;
		case "playlist":
			foundReview = reviewRepository.findByPlaylist_SpotifyId(entityId);
			break;
		case "albums":
			foundReview = reviewRepository.findByAlbums_SpotifyId(entityId);
			break;
		default:
			System.out.println("Object has not been found.");
			break;
		}
		return foundReview.orElseThrow(()-> new ResourceNotFoundException("Object has not been found"));
	}

	@Override
	@Transactional
	public String addReview(Reviews review, String entityId, String entityType, String userId) {
		Optional<Users> optionalUser = Optional.of(userRepository.findByRefId(userId));

		if (optionalUser.isPresent()) {
			Reviews newReview = new Reviews();
			newReview.setUser(optionalUser.get());
			newReview.setName(review.getName());
			newReview.setRating(review.getRating());
			newReview.setComment(review.getComment());

			switch (entityType) {
			case "songs":
				Optional<Songs> optionalSong = songRepository.findSongBySpotifyId(entityId);
				if (optionalSong.isPresent()) {
					newReview.setSongs(optionalSong.get());
					optionalSong.get().getReviews().add(newReview);
					reviewRepository.save(newReview);
					return "Review for song has been added";
				}
				break;

			case "albums":
				Optional<Albums> optionalAlbum = albumRepository.findAlbumBySpotifyId(entityId);
				if (optionalAlbum.isPresent()) {
					newReview.setAlbums(optionalAlbum.get());
					optionalAlbum.get().getReviews().add(newReview);
					reviewRepository.save(newReview);
					return "Review for album has been added";
				}
				break;

			case "playlist":
				Optional<Playlists> optionalPlaylist = playlistRepository.findPlaylistBySpotifyId(entityId);
				if (optionalPlaylist.isPresent()) {
					newReview.setPlaylist(optionalPlaylist.get());
					optionalPlaylist.get().getReviews().add(newReview);
					reviewRepository.save(newReview);
					return "Review for playlist has been added";
				}
				break;

			default:
				throw new ResourceNotFoundException("Object not found");
			}
		}

		return "User not found";
	}

}
