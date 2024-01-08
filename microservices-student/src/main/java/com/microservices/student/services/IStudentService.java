package com.microservices.student.services;


import com.microservices.student.models.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService
{
    List<Student> findAll();
    Student findById(Long id);
    void save(Student student);
    List<Student> findByIdCourse(Long idCourse);
}
