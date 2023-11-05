package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
