package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
