package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Albums;

public interface AlbumRepository extends JpaRepository<Albums, Long> {

}
