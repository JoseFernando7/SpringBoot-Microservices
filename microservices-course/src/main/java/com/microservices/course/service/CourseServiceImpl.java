package com.microservices.course.service;

import com.microservices.course.client.StudentClient;
import com.microservices.course.dto.StudentDTO;
import com.microservices.course.http.response.StudentsByCourseResponse;
import com.microservices.course.model.Course;
import com.microservices.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService
{
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentClient studentClient;

    @Override
    public List<Course> findAll()
    {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course findById(Long id)
    {
        return courseRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Course course)
    {
        courseRepository.save(course);
    }

    @Override
    public StudentsByCourseResponse findStudentsByCourseId(Long idCourse)
    {
        // Consultar el curso
        Course course = courseRepository.findById(idCourse).orElse(new Course());

        // Obtener los estudiantes
        List<StudentDTO> studentDTOList = studentClient.findAllStudentsByCourse(idCourse);

        return StudentsByCourseResponse.builder()
                .courseName(course.getName())
                .teacher(course.getTeacher())
                .studentDTOList(studentDTOList)
                .build();
    }
}
