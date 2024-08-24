package edu.icet2.service;

import edu.icet2.dto.Student;

import java.util.List;

public interface StudentService {

    List<Student> getStudent();


    void addStudent(Student student);
}
