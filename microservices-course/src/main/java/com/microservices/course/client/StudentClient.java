package com.microservices.course.client;

import com.microservices.course.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// Para realizar un "fetch" de datos del microservicio student
@FeignClient(name = "msvc-student", url = "localhost:8090/api/v1/students")
public interface StudentClient
{
    @GetMapping("/search-by-course/{idCourse}")
    List<StudentDTO> findAllStudentsByCourse(@PathVariable Long idCourse);
}
