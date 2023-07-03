package com.example.controller;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.example.model.Customer;
import com.example.model.User;
import com.example.repository.CustomerRepository;
import com.example.repository.UserRepository;
import com.example.service.CustomerService;
import com.example.util.LoggerUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostCustomer() {
        // Create a sample customer object
        Customer customer = new Customer();
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        customer.setUser(user);

        // Mock the necessary dependencies and method calls
        when(passwordEncoder.encode(eq("testpassword"))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Invoke the API endpoint
        ResponseEntity<String> response = customerController.postCustomer(customer);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer added ...", response.getBody());
    }

    @Test
    public void testGetAllCustomer() {
        // Create a sample list of customers
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        // Mock the necessary dependencies and method calls
        when(customerService.getAllCustomer()).thenReturn(customers);

        // Invoke the API endpoint
        List<Customer> result = customerController.getAllCustomer();

        // Verify the response
        assertNotNull(result);
        assertEquals(customers.size(), result.size());
    }

    @Test
    public void testGetCustomerById() throws ResourceNotFoundException {
        // Create a sample customer
        Customer customer = new Customer();
        customer.setId(1);

        // Mock the necessary dependencies and method calls
        when(customerService.findById(1)).thenReturn(Optional.of(customer));

        // Invoke the API endpoint
        ResponseEntity<Object> response = customerController.getCustomerById(1);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testUpdateCustomer() throws ResourceNotFoundException {
        // Create a sample customer
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("John Doe");

        // Mock the necessary dependencies and method calls
        when(customerService.findById(1)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(updatedCustomer);

        // Invoke the API endpoint
        ResponseEntity<Customer> response = customerController.updateCustomer(1, updatedCustomer);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomer, response.getBody());
    }

    @Test
    public void testDeleteCustomer() throws ResourceNotFoundException {
        // Mock the necessary dependencies and method calls
        when(customerService.findById(1)).thenReturn(Optional.of(new Customer()));

        // Invoke the API endpoint
        Map<String, Boolean> response = customerController.deleteCustomer(1);

        // Verify the response
        assertNotNull(response);
        assertEquals(Boolean.TRUE, response.get("deleted"));
    }
}
