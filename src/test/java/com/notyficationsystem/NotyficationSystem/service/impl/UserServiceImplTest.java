package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.repository.UserRepo;
import com.notyficationsystem.NotyficationSystem.model.constant.Role;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("John Doe", "john.doe@example.com", Role.USER, "1234567890", "password", 1L);
    }

    @Test
    @DisplayName("testCreateWhenValidUserThenReturnUser")
    void testCreateWhenValidUserThenReturnUser() {
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenReturn(user);
        User createdUser = userService.create(user);
        assertThat(createdUser).isEqualTo(user);
    }

    @Test
    @DisplayName("testCreateWhenNullUserThenThrowException")
    void testCreateWhenNullUserThenThrowException() {
        assertThatThrownBy(() -> userService.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("person is not valid");
    }

    @Test
    @DisplayName("testUpdateWhenValidUserThenReturnUpdatedUser")
    void testUpdateWhenValidUserThenReturnUpdatedUser() {
        Mockito.when(userRepo.findByFullname(user.getFullname())).thenReturn(user);
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenReturn(user);
        User updatedUser = userService.update(user);
        assertThat(updatedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(updatedUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
    }

    @Test
    @DisplayName("testUpdateWhenUserNotFoundThenThrowException")
    void testUpdateWhenUserNotFoundThenThrowException() {
        Mockito.when(userRepo.findByFullname(user.getFullname())).thenReturn(null);
        assertThatThrownBy(() -> userService.update(user))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("testReadByEmailWhenValidEmailThenReturnUser")
    void testReadByEmailWhenValidEmailThenReturnUser() {
        User foundUser = userService.readByEmail("john.doe@example.com");
        List<User> list = userService.getAll();
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    @DisplayName("testReadByEmailWhenUserNotFoundThenThrowException")
    void testReadByEmailWhenUserNotFoundThenThrowException() {
        Mockito.when(userRepo.findByEmail(user.getEmail())).thenReturn(null);
        assertThatThrownBy(() -> userService.readByEmail(user.getEmail()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("testReadByPhoneWhenValidPhoneThenReturnUser")
    void testReadByPhoneWhenValidPhoneThenReturnUser() {
        Mockito.when(userRepo.findByPhoneNumber(user.getPhoneNumber())).thenReturn(user);
        User foundUser = userService.readByPhone(user.getPhoneNumber());
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    @DisplayName("testReadByPhoneWhenUserNotFoundThenThrowException")
    void testReadByPhoneWhenUserNotFoundThenThrowException() {
        Mockito.when(userRepo.findByPhoneNumber(user.getPhoneNumber())).thenReturn(null);
        assertThatThrownBy(() -> userService.readByPhone(user.getPhoneNumber()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("testReadByFullNameWhenValidFullNameThenReturnUser")
    void testReadByFullNameWhenValidFullNameThenReturnUser() {
        Mockito.when(userRepo.findByFullname(user.getFullname())).thenReturn(user);
        User foundUser = userService.readByFullName(user.getFullname());
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    @DisplayName("testReadByFullNameWhenUserNotFoundThenThrowException")
    void testReadByFullNameWhenUserNotFoundThenThrowException() {
        Mockito.when(userRepo.findByFullname(user.getFullname())).thenReturn(null);
        assertThatThrownBy(() -> userService.readByFullName(user.getFullname()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("testReadByIdWhenValidIdThenReturnUser")
    void testReadByIdWhenValidIdThenReturnUser() {
        Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        User foundUser = userService.readById(user.getId());
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    @DisplayName("testReadByIdWhenUserNotFoundThenThrowException")
    void testReadByIdWhenUserNotFoundThenThrowException() {
        Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.readById(user.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("worng id was delivered");
    }

    @Test
    @DisplayName("testGetAllThenReturnListOfUsers")
    void testGetAllThenReturnListOfUsers() {
        List<User> users = List.of(user);
        Mockito.when(userRepo.findAll()).thenReturn(users);
        List<User> foundUsers = userService.getAll();
        assertThat(foundUsers).isEqualTo(users);
    }

    @Test
    @DisplayName("testDeleteWhenValidIdThenDeleteUser")
    void testDeleteWhenValidIdThenDeleteUser() {
        Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepo).delete(Mockito.any(User.class));
        userService.delete(user.getId());
        Mockito.verify(userRepo, Mockito.times(1)).delete(user);
    }
}
