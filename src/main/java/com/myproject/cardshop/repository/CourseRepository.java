package com.myproject.cardshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.cardshop.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer>{

}
