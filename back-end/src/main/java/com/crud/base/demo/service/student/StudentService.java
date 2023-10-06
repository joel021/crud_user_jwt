package com.crud.base.demo.service.student;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Student;
import com.crud.base.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService extends StudentHandler {

    @Autowired
    private StudentRepository studentRepository;

    public Student register(Student student) {

        return studentRepository.save(student);
    }

    public Student findStudentByRegister(String register) throws ResourceNotFoundException {

        return existsOrThrow(studentRepository.findById(register));
    }
}
