package com.crud.base.demo.service;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Student;
import com.crud.base.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student register(Student student) {

        return studentRepository.save(student);
    }

    public Student findStudentByRegister(String register) throws ResourceNotFoundException {

        Optional<Student> optionalStudent = studentRepository.findByRegister(register);

        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        }
        throw new ResourceNotFoundException("Student not found.");
    }
}
