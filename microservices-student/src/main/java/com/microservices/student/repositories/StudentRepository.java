package com.microservices.student.repositories;

import com.microservices.student.models.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>
{
    @Query("SELECT s FROM Student s WHERE s.courseId = :idCourse")
    List<Student> findAllStudentsByCourse(Long idCourse);
}
