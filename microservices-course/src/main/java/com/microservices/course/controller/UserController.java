package com.microservices.course.controller;

import com.microservices.course.http.request.CreateUserDTO;
import com.microservices.course.model.ERole;
import com.microservices.course.model.Role;
import com.microservices.course.model.UserEntity;
import com.microservices.course.repository.UserRespository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserRespository userRespository;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO)
    {
        // Recorre los roles del objeto pasado por POST y crea un nuevo set de roles
        Set<Role> roles = createUserDTO.getRoles().stream()
                .map(role -> Role.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        // Crea un objeto UserEntity con los datos pasados
        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRespository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String id)
    {
        userRespository.deleteById(Long.parseLong(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
