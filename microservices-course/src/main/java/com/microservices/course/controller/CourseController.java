package com.microservices.course.controller;

import com.microservices.course.model.Course;
import com.microservices.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController
{
    @Autowired
    private ICourseService courseService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCourse(@RequestBody Course course)
    {
        courseService.save(course);
    }

    // No asegurar
    @GetMapping("/")
    public ResponseEntity<?> findAllCourses()
    {
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findCourseById(@PathVariable Long id)
    {
        return ResponseEntity.ok(courseService.findById(id));
    }

    // Asegurar
    @GetMapping("/search-students/{idCourse}")
    public ResponseEntity<?> findStudentsByCourseId(@PathVariable Long idCourse)
    {
        return ResponseEntity.ok(courseService.findStudentsByCourseId(idCourse));
    }
}
