//package com.notyficationsystem.NotyficationSystem.service.impl;
//
//import com.notyficationsystem.NotyficationSystem.model.User;
//import com.notyficationsystem.NotyficationSystem.model.constant.Role;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class UserDetailsServiceImplTest {
//
//    @Mock
//    private UserServiceImpl userService;
//
//    @InjectMocks
//    private UserDetailsServiceImpl userDetailsService;
//
//    private User user;
//
//    @BeforeEach
//    public void setUp() {
//        user = new User("John Doe", "john.doe@example.com", Role.USER, "1234567890", "password", 1L);
//    }
//
//    @Test
//    public void testLoadUserByUsernameWhenValidUsernameThenReturnUserDetails() {
//        when(userService.readByEmail("john.doe@example.com")).thenReturn(user);
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername("john.doe@example.com");
//
//        assertEquals(user, userDetails);
//    }
//
//    @Test
//    public void testLoadUserByUsernameWhenInvalidUsernameThenReturnNull() {
//        when(userService.readByEmail("invalid@example.com")).thenReturn(null);
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername("invalid@example.com");
//
//        assertNull(userDetails);
//    }
//}