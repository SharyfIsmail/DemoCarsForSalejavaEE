package userTest;

import com.example.demoCarsForSale.controllers.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.controllers.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.services.impl.UserDeleteService;
import com.example.demoCarsForSale.services.impl.UserLoginService;
import com.example.demoCarsForSale.services.impl.UserSignUpService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsetTest {
    public static final UserSignUpRequest USER_SIGN_UP_REQUEST = new UserSignUpRequest();
    public static final UserLogInRequest USER_LOG_IN_REQUEST = new UserLogInRequest();
    public static final UserDeleteService USER_DELETE_SERVICE = new UserDeleteService();
    public static final UserSignUpService USER_SIGN_UP_SERVICE = new UserSignUpService();
    public static final UserLoginService USER_LOGIN_SERVICE = new UserLoginService();

    private static UserResponse userResponse = null;

    static {
        USER_SIGN_UP_REQUEST.setUserEmail("xandrey95x@gmail.com");
        USER_SIGN_UP_REQUEST.setUserName("Andrey");
        USER_SIGN_UP_REQUEST.setUserPassword("ABS");

        USER_LOG_IN_REQUEST.setUserEmail("xandrey95x@gmail.com");
        USER_LOG_IN_REQUEST.setUserPassword("ABS");
    }

    @BeforeAll
    public static void createUser() {
        userResponse = USER_SIGN_UP_SERVICE.createUser(USER_SIGN_UP_REQUEST);
        assertEquals(userResponse.getUserName(), USER_SIGN_UP_REQUEST.getUserName());
    }

    @Test
    public void logIn() {
        USER_LOGIN_SERVICE.logIn(USER_LOG_IN_REQUEST);
        assertEquals(userResponse.getUserName(), USER_SIGN_UP_REQUEST.getUserName());
    }

    @AfterAll
    public static void deleteUser() {
        USER_DELETE_SERVICE.delete(userResponse.getUserId());
    }
}
