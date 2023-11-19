package com.proj.music.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.music.repository.AlbumRepository;
import com.proj.music.repository.ArtistRepository;
import com.proj.music.repository.ReviewRepository;
import com.proj.music.repository.SongRepository;
import com.proj.music.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SongRepository songRepository;
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Override
	public Object searchItems(String q, String type) {
		Object o1 = null;
		switch(type)
		{
			case "album":
				break;
				
			case "song":
//				o1 = songRepository.findByNameContainingAndType(q, type);
				break;
				
			case "artist":
				break;
				
			case "review":
				break;
			
			default:
				break;
		}
		return o1;
	}
	
}
