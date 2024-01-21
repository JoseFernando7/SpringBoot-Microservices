package com.microservices.student.controller;

import com.microservices.student.models.Student;
import com.microservices.student.services.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController
{
    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private IStudentService studentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStudent(@RequestBody Student student)
    {
        studentService.save(student);
    }

    //Endpoint SECURED
    @GetMapping("/")
    public ResponseEntity<?> findAllStudents()
    {
        return ResponseEntity.ok(studentService.findAll());
    }

    //Endpoint NOT SECURED
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

    /* Este endpoint de controlador es para mostrar los datos del inicio de sesión del usuario que se especificó en
    el security config. */
    @GetMapping("/session")
    public ResponseEntity<?> getSessionDetails()
    {
        String sessionId = "";
        User userObject = null;

        List<Object> sessions = sessionRegistry.getAllPrincipals();

        for (Object session : sessions)
        {
            if (session instanceof User)
            {
                userObject = (User) session;
            }

            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false);

            for (SessionInformation sessionInformation : sessionInformations)
            {
                sessionId = sessionInformation.getSessionId();
            }
        }

        Map<String, Object> response = new HashMap<>();

        response.put("response", "Hello World");
        response.put("sessionId", sessionId);
        response.put("sessionUser", userObject);

        return ResponseEntity.ok(response);
    }
}
