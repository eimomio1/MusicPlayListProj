package com.proj.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.music.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
