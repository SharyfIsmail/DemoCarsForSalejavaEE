package service_test;

import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.exceptions.EntityNotFoundException;
import com.example.demoCarsForSale.pojo.User;
import com.example.demoCarsForSale.repository.UserRepository;
import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.services.impl.UserServiceImpl;
import com.example.demoCarsForSale.web.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.web.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    private UserService userService;
    private static UserResponse userResponse;
    private static UserSignUpRequest userSignUpRequest;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        userSignUpRequest = UserSignUpRequest.builder()
            .userEmail("xandrey95x@live.ru")
            .userName("Sharyf")
            .userPassword("blablablabla")
            .build();
    }

    @Test
    public void successSignupWithValidUser() {
        User user = User.builder()
            .email("xandrey95x@live.ru")
            .name("Sharyf")
            .password("blablablabla")
            .userId(1L)
            .build();

        Mockito.when(userRepository.existsByEmail(ArgumentMatchers.any(String.class))).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        UserResponse userResponse = userService.createUser(userSignUpRequest);

        Assertions.assertEquals(userResponse.getUserName(), user.getName());
    }

    @Test
    public void failedSignUpWithInvalidUser() {
        Mockito.when(userRepository.existsByEmail(ArgumentMatchers.any(String.class))).thenReturn(true);
        Assertions.assertThrows(BadRequestException.class, () -> userService.createUser(userSignUpRequest));

        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(ArgumentMatchers.anyString());
    }

    @Test
    public void successUpdateValidUser() {
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
            .userEmail("someEmail")
            .userName("someName")
            .userPassword2("somePassword")
            .userPassword1("somePassword")
            .build();
        User user = User.builder()
            .userId(1L)
            .name("oldName")
            .email("oldEmail")
            .password("oldPassword")
            .build();

        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(user));

        Assertions.assertDoesNotThrow(() -> userService.updateUser(userUpdateRequest, 1L));

        Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
    }

    @Test
    public void failedUpdateValidUser_MismatchOfPasswords() {
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
            .userEmail("someEmail")
            .userName("someName")
            .userPassword2("somePassword")
            .userPassword1("otherPassword")
            .build();

        Assertions.assertThrows(BadRequestException.class, () -> userService.updateUser(userUpdateRequest, 1L));
    }

    @Test
    public void failedUpdateValidUser_MissingUser() {
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
            .userEmail("someEmail")
            .userName("someName")
            .userPassword2("somePassword")
            .userPassword1("somePassword")
            .build();
        Mockito.doReturn(Optional.empty()).when(userRepository).findById(ArgumentMatchers.anyLong());
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.updateUser(userUpdateRequest, 1L));

        Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
    }
}
