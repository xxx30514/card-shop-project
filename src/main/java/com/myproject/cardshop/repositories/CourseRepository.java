package com.myproject.cardshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.cardshop.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Integer>{

}
