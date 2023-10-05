package com.crud.base.demo.controller;

import com.crud.base.demo.exceptions.NotAllowedException;
import com.crud.base.demo.model.Course;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/users/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> courseMap){

        Course course = null;
        try {
            course = Course.getInstance(courseMap);
        } catch (NotAllowedException e) {

            Map<String, List<String>> errorResp = new HashMap<>();
            errorResp.put("errors", Collections.singletonList(e.getMessage()));

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(errorResp);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.save(user.getId(), course));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllByUserId(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.findByUserId(user.getId()));
    }

    @GetMapping("/search")
    public ResponseEntity<?> seachByName(@RequestParam("name") String name){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.findByName(user.getId(), name));
    }

}
