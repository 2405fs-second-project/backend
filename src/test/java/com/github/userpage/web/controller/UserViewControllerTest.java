//package com.github.userpage.web.controller;
//
//import com.github.userpage.model.User;
//import com.github.userpage.service.UserService;
//import com.github.userpage.web.dto.ViewUserResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static com.github.userpage.model.User.Gender.FEMALE;
//import static com.github.userpage.model.User.Gender.MALE;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(UserViewController.class)
//public class UserViewControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private UserViewController userViewController;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(userViewController).build();
//    }
//
//    @Test
//    public void testGetUsers() throws Exception {
//        // Given
//        User user1 = User.builder()
//                .id(1)
//                .name("지우개")
//                .email("test@test.com")
//                .password("password")
//                .phoneNum("010000")
//                .address("서울시")
//                .gender(FEMALE)
//                .profilePictureUrl("www.naver.com")
//                .aboutMe("나욤")
//                .build();
//
//        User user2 = User.builder()
//                .id(2)
//                .name("연필")
//                .email("apple@test.com")
//                .password("password")
//                .phoneNum("01234")
//                .address("경기도")
//                .gender(MALE)
//                .profilePictureUrl("www.daum.com")
//                .aboutMe("화이팅")
//                .build();
//
//        List<User> users = Arrays.asList(user1, user2);
//
//        when(userService.findAll()).thenReturn(users);
//
//        // When and Then
//        mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("userList"))
//                .andExpect(model().attributeExists("users"))
//                .andExpect(model().attribute("users", org.hamcrest.Matchers.hasSize(users.size())))
//                .andExpect(model().attribute("users", org.hamcrest.Matchers.everyItem(org.hamcrest.Matchers.instanceOf(ViewUserResponse.class))));
//    }
//
//    @Test
//    public void testGetUser() throws Exception {
//        // Given
//        int userId = 1;
//        User user = User.builder()
//                .id(userId)
//                .name("지우개")
//                .email("test@test.com")
//                .password("password")
//                .phoneNum("010000")
//                .address("서울시")
//                .gender(User.Gender.FEMALE) // Enum 값 수정
//                .profilePictureUrl("www.naver.com")
//                .aboutMe("나욤")
//                .build();
//
//        when(userService.findById(userId)).thenReturn(user);
//
//        // When and Then
//        mockMvc.perform(get("/users/{id}", userId))
//                .andExpect(status().isOk())
//                .andExpect(view().name("userDetail"))
//                .andExpect(model().attributeExists("user"))
//                .andExpect(model().attribute("user", new ViewUserResponse(user)));
//    }
//
//
//    @Test
//    public void testGetUserApi() throws Exception {
//        // Given
//        int userId = 1;
//        User user = User.builder()
//                .id(userId)
//                .name("지우개")
//                .email("test@test.com")
//                .password("password")
//                .phoneNum("0123456")
//                .address("서울시")
//                .gender(User.Gender.FEMALE)
//                .profilePictureUrl("www.naver.com")
//                .aboutMe("나욤")
//                .build();
//
//        // Mockito를 사용하여 findById 메소드의 반환 값을 설정합니다.
//        doReturn(Optional.of(user)).when(userService).findById(userId);
//
//        // When and Then
//        mockMvc.perform(get("/users/api/{id}", userId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.id").value(user.getId()))
//                .andExpect(jsonPath("$.name").value(user.getName()))
//                .andExpect(jsonPath("$.email").value(user.getEmail()))
//                .andExpect(jsonPath("$.phone").value(user.getPhoneNum()))
//                .andExpect(jsonPath("$.address").value(user.getAddress()))
//                .andExpect(jsonPath("$.website").value(user.getProfilePictureUrl()))
//                .andExpect(jsonPath("$.gender").value(user.getGender().name()))
//                .andExpect(jsonPath("$.nickname").value(user.getAboutMe()));
//    }
//}
