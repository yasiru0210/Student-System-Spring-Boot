package edu.icet2.service;

import edu.icet2.dto.Student;
import edu.icet2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository repository;

    public List<Student> getStudent() {
       List<Student> all =repository.findAll();
       return all;
    }

    @Override
    public void addStudent(Student student) {

        repository.save(student);
    }





}
