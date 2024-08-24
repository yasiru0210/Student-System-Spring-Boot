package edu.icet2.controller;

import edu.icet2.dto.Student;
import edu.icet2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class StudentController {
    @Autowired
    StudentService service;

    @GetMapping("/get")
    public List<Student> getStudent(){
        return service.getStudent();}

    @PostMapping
    public void addStudent( @RequestBody Student student) {
       service.addStudent(student);
    }
}
