package edu.icet2.service;

import edu.icet2.dto.Student;
import edu.icet2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
@Primary
@Service

public class StudentServiceImpl2 implements StudentService {
    @Autowired
    StudentRepository repository;

    public List<Student> getStudent() {

        return repository.findAll();
    }

    @Override
    public void addStudent(Student student) {

        repository.save(student);
        repository.save(student);
    }





}
