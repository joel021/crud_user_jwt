package com.crud.base.demo.controller;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Student;
import com.crud.base.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<Student> register(@Valid Student student) {

        return ResponseEntity.ok().body(studentService.register(student));
    }

    @GetMapping("/{register}")
    @PreAuthorize("hasAuthority(1)")
    public ResponseEntity<Student> findStudentByRegister(@PathVariable String register) throws ResourceNotFoundException {

        return ResponseEntity.ok().body(studentService.findStudentByRegister(register));
    }
}
