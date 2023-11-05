package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

}
