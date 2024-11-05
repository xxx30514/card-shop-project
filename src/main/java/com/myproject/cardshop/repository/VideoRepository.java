package com.myproject.cardshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.cardshop.model.Video;

public interface VideoRepository extends JpaRepository<Video, Integer> {

}
