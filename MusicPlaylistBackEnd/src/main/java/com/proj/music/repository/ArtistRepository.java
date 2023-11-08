package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
