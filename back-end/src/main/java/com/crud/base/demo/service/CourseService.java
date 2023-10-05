package com.crud.base.demo.service;

import com.crud.base.demo.model.Course;
import com.crud.base.demo.repository.CourseRepository;
import com.crud.base.demo.service.user.SearchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SearchUserService searchUserService;

    public Course save(UUID userId, Course course) {
        course.setUserId(userId);
        return courseRepository.save(course);
    }

    public List<Course> findByUserId(UUID userId){
        return courseRepository.findByUserIdOrderByYearDesc(userId);
    }

    public List<Course> findByName(UUID userId, String name){
        return courseRepository.findByUserIdAndNameContainingOrderByYearDesc(userId, name);
    }
}
