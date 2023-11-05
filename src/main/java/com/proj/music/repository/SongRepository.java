package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long> {

}
