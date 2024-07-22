package com.github.userpage.service;

import com.github.userpage.model.Users;
import com.github.userpage.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UsersRepository userRepository;

    @InjectMocks
    private UsersService userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_returnListOfUsers() {
        //given
        Users user1 = Users.builder().id(1).name("김똥띵").build();
        Users user2 = Users.builder().id(2).name("정똥띵").build();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        //when
        List<Users> users = userService.findAll();

        //then
        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById_returnUser() {
        //given
        Users user = Users.builder().id(1).name("김똥띵").build();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        //when
        Users foundUser = userService.findById(1);

        //then
        assertNotNull(foundUser);
        assertEquals("김똥띵", foundUser.getName());
        verify(userRepository, times(1)).findById(1);

    }
}
