package com.crud.base.demo.repository;

import com.crud.base.demo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByUserIdOrderByYearDesc(UUID userId);

    List<Course> findByUserIdAndNameContainingOrderByYearDesc(UUID userId, String name);
}
