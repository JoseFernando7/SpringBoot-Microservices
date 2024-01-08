package com.microservices.course.service;

import com.microservices.course.http.response.StudentsByCourseResponse;
import com.microservices.course.model.Course;

import java.util.List;

public interface ICourseService
{
    List<Course> findAll();
    Course findById(Long id);
    void save(Course course);
    StudentsByCourseResponse findStudentsByCourseId(Long idCourse);
}
