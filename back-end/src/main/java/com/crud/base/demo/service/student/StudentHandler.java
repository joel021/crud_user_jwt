package com.crud.base.demo.service.student;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Student;


import java.util.Optional;

public class StudentHandler {

    public Student register(Student student) {

        return student;
    }

    public Student existsOrThrow(Optional<Student> optionalStudent) throws ResourceNotFoundException {

        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        }
        throw new ResourceNotFoundException("Student not found.");
    }
}
