package com.microservices.student.controller;

import com.microservices.student.models.Student;
import com.microservices.student.services.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController
{
    @Autowired
    private IStudentService studentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStudent(@RequestBody Student student)
    {
        studentService.save(student);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllStudents()
    {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findStudentById(@PathVariable Long id)
    {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @GetMapping("/search-by-course/{idCourse}")
    public ResponseEntity<?> findByIdCourse(@PathVariable Long idCourse)
    {
        return ResponseEntity.ok(studentService.findByIdCourse(idCourse));
    }
}
