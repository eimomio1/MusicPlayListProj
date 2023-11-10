package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Reviews;

public interface ReviewRepository extends JpaRepository<Reviews, Long> {

}
