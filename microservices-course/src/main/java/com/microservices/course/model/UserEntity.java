package com.microservices.course.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

// Class for users in spring security
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Size(max = 80)
    private String email;

    @NotBlank
    @Size(max = 30)
    private String username;

    @NotBlank
    private String password;

    // Para relacionar las tablas 'users' y 'roles'.
    // Relación de muchos a muchos. Fetch para obtener todos los registros que coincidan en lugar de a uno (LAZY)
    // Target Entity para dar a entender que se quiere relacionar con la clase de la entidad role.
    /* Cascade para indicarle que agregue los roles en cascada a la BD pero que solo aplique al agregarlos (PERSIST)
        para que, cuando se elimine un usuario, no elimine a todos los roles de la otra tabla ya que afectaría a los
        demás usuarios.
    */
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
