package userTest;

import com.example.demoCarsForSale.web.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.web.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.services.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsetTest {
//    public static final UserSignUpRequest USER_SIGN_UP_REQUEST = new UserSignUpRequest();
//    public static final UserLogInRequest USER_LOG_IN_REQUEST = new UserLogInRequest();
//    public static final UserService USER_SERVICE = new UserServiceImpl();
//    private static UserResponse userResponse = null;
//
//    static {
//        USER_SIGN_UP_REQUEST.setUserEmail("xandrey95x@gmail.com");
//        USER_SIGN_UP_REQUEST.setUserName("Andrey");
//        USER_SIGN_UP_REQUEST.setUserPassword("ABS");
//
//        USER_LOG_IN_REQUEST.setUserEmail("xandrey95x@gmail.com");
//        USER_LOG_IN_REQUEST.setUserPassword("ABS");
//    }
//
//    @BeforeAll
//    public static void createUser() {
//        userResponse = USER_SERVICE.createUser(USER_SIGN_UP_REQUEST);
//        assertEquals(userResponse.getUserName(), USER_SIGN_UP_REQUEST.getUserName());
//    }
//
//    @Test
//    public void logIn() {
//        USER_SERVICE.logIn(USER_LOG_IN_REQUEST);
//        assertEquals(userResponse.getUserName(), USER_SIGN_UP_REQUEST.getUserName());
//    }
//
//    @AfterAll
//    public static void deleteUser() {
//        USER_SERVICE.delete(userResponse.getUserId());
//    }
}
