package com.myproject.cardshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.cardshop.entities.Video;

public interface VideoRepository extends JpaRepository<Video, Integer> {

}
