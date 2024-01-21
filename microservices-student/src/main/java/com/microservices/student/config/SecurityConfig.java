package com.microservices.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/students/search/{id}").permitAll() // Permite el acceso a este endpoint
                        .anyRequest().authenticated()) // Para cualquier otro endpoint requiera el inicio de sesión
                .formLogin((form) -> form
                        .successHandler(successHandler()) // Ejecuta la función succesHandler cuando el inicio de sesión haya funcionado correctamente
                        .permitAll()) // Para que cualquiera pueda acceder al formulario de inicio de sesión
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Especificar cuando crear una nueva sesión. ALWAYS: Pide un inicio si no hay y sigue reutilizando la que se crea hasta que se cierra.
                        .invalidSessionUrl("/login") // Redirige al usuario al endpoint especificado si el inicio de sesión es incorrecto
                        .maximumSessions(1) // Cantidad de sesiones permitidas
                        .expiredUrl("/login") // Redirige al usuario al endpoint especificado cuando la sesión expire por tiempo de inactividad
                        .sessionRegistry(sessionRegistry())) // Llama a la función sessionRegistry() para recopilar los datos de inicion de sesión del usuario
                .sessionManagement((fixSession) -> fixSession
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession)) // Para migrar los datos de una sesión a otra cada vez que se cree para evitar ataques.
                .build();


//                .authorizeHttpRequests()
//                    .requestMatchers("/api/v1/students/search/").permitAll() // Permite el acceso a este endpoint
//                    .anyRequest().authenticated() // Para cualquier otro endpoint requiera el inicio de sesión
//                .and()
//                .formLogin().permitAll()
//                .and()
//                .build();
    }

    // Esta función retorna los datos del registro del usuario para hacerle un seguimiento.
    @Bean
    public SessionRegistry sessionRegistry()
    {
        return new SessionRegistryImpl();
    }

    // Esto es para redirigir al usuario al endpoint deseado después de que se haya logeado correctamente
    public AuthenticationSuccessHandler successHandler()
    {
        return ((request, response, authentication) -> response.sendRedirect("/api/v1/students/session"));
    }
}
