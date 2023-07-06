package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Admin;
import com.example.model.User;
import com.example.repository.AdminRepository;
import com.example.repository.UserRepository;
import com.example.service.AdminService;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //	Add Admin
	@PostMapping("/add")
	public ResponseEntity<String> postadmin(@RequestBody Admin admin) {
		
//		Fetch User info from employee input and save it in DB 
				User user = admin.getUser(); //I have username and password 
				//I will assign the role
				user.setRole("admin");

				//Converting plain text password into encoded text
				String encodedPassword = passwordEncoder.encode(user.getPassword());
				//attach encoded password to user
				user.setPassword(encodedPassword);

				user  = userRepository.save(user);

				//Attach user object to employee
				admin.setUser(user);
		
		adminService.postadmin(admin);
		return ResponseEntity.status(HttpStatus.OK).body("admin added ...");
	}


    @Test
    public void testGetAllAdmin() {
        List<Admin> admins = new ArrayList<>();
        admins.add(new Admin());
        admins.add(new Admin());

        when(adminService.getAlladmin()).thenReturn(admins);

        List<Admin> response = adminController.getAlladmin();

        assertEquals(admins, response);

        verify(adminService).getAlladmin();
    }

    @Test
    public void testGetAdminById() {
        Long adminId = 1L;
        Admin admin = new Admin();
        admin.setId(adminId);

        when(adminService.findById(adminId)).thenReturn(Optional.of(admin));

        ResponseEntity<Object> response = adminController.getadminById(adminId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admin, response.getBody());

        verify(adminService).findById(adminId);
    }

    @Test
    public void testGetAdminById_InvalidId() {
        Long adminId = 1L;

        when(adminService.findById(adminId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = adminController.getadminById(adminId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid ID Given", response.getBody());

        verify(adminService).findById(adminId);
    }

    @Test
    public void testUpdateAdmin() throws ResourceNotFoundException {
        Long adminId = 1L;
        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setName("John Doe");

        Admin updatedAdmin = new Admin();
        updatedAdmin.setId(adminId);
        updatedAdmin.setName("John Doe Updated");

        when(adminService.findById(adminId)).thenReturn(Optional.of(admin));
        when(adminRepository.save(any(Admin.class))).thenReturn(updatedAdmin);

        ResponseEntity<Admin> response = adminController.updateadmin(adminId, updatedAdmin);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAdmin, response.getBody());

        verify(adminService).findById(adminId);
        verify(adminRepository).save(any(Admin.class));
    }

    @Test
    public void testUpdateAdmin_InvalidId() {
        Long adminId = 1L;
        Admin updatedAdmin = new Admin();
        updatedAdmin.setId(adminId);
        updatedAdmin.setName("John Doe Updated");

        when(adminService.findById(adminId)).thenReturn(Optional.empty());

        try {
            adminController.updateadmin(adminId, updatedAdmin);
        } catch (ResourceNotFoundException ex) {
            assertEquals("admin not exist with id: " + adminId, ex.getMessage());
        }

        verify(adminService).findById(adminId);
        verify(adminRepository, never()).save(any(Admin.class));
    }

    @Test
    public void testDeleteAdmin() throws ResourceNotFoundException {
        Long adminId = 1L;
        Admin admin = new Admin();
        admin.setId(adminId);

        when(adminService.findById(adminId)).thenReturn(Optional.of(admin));

        Map<String, Boolean> expectedResponse = new HashMap<>();
        expectedResponse.put("deleted", true);

        Map<String, Boolean> response = adminController.deleteadmin(adminId);

        assertEquals(expectedResponse, response);

        verify(adminService).findById(adminId);
        verify(adminService).deleteById(adminId);
    }

    @Test
    public void testDeleteAdmin_InvalidId() {
        Long adminId = 1L;

        when(adminService.findById(adminId)).thenReturn(Optional.empty());

        try {
            adminController.deleteadmin(adminId);
        } catch (ResourceNotFoundException ex) {
            assertEquals("admin not found for this id :: " + adminId, ex.getMessage());
        }

        verify(adminService).findById(adminId);
        verify(adminService, never()).deleteById(adminId);
    }
}
