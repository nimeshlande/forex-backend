package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.model.User;
import com.example.repository.AdminRepository;
import com.example.repository.UserRepository;

public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // public void testSignUp() {
    //     User user = new User();
    //     user.setUsername("john.doe");
    //     user.setPassword("password");

    //     when(userRepository.getUserByUsername(user.getUsername())).thenReturn(null);
    //     when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
    //     when(userRepository.save(any(User.class))).thenReturn(user);

    //     ResponseEntity<String> response = userController.signUp(user);

    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals("Signup Success", response.getBody());

    //     verify(userRepository).getUserByUsername(user.getUsername());
    //     verify(passwordEncoder).encode(user.getPassword());
    //     verify(userRepository).save(any(User.class));
    //     }

    @Test
    public void testSignUp_UsernameConflict() {
        User user = new User();
        user.setUsername("john.doe");
        user.setPassword("password");

        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(new User());

        ResponseEntity<String> response = userController.signUp(user);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Username already present", response.getBody());

        verify(userRepository).getUserByUsername(user.getUsername());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLogin() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("john.doe");

        User user = new User();
        user.setUsername("john.doe");
        user.setRole("customer");

        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<Object> response = userController.login(principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

        verify(userRepository).getUserByUsername(user.getUsername());
    }

    @Test
    public void testLogin_InvalidRole() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("john.doe");

        User user = new User();
        user.setUsername("john.doe");
        user.setRole("admin");

        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<Object> response = userController.login(principal);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Signup failed", response.getBody());

        verify(userRepository).getUserByUsername(user.getUsername());
    }

    @Test
    public void testAdminLogin() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("admin");

        User user = new User();
        user.setUsername("admin");
        user.setRole("admin");

        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<Object> response = userController.adminLogin(principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

        verify(userRepository).getUserByUsername(user.getUsername());
    }

    @Test
    public void testAdminLogin_InvalidRole() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("admin");

        User user = new User();
        user.setUsername("admin");
        user.setRole("customer");

        when(userRepository.getUserByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<Object> response = userController.adminLogin(principal);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Signup failed", response.getBody());

        verify(userRepository).getUserByUsername(user.getUsername());
    }
}
