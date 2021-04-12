import com.example.demoCarsForSale.BootApplication;
import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.web.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.web.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BootApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTests {
    private static UserResponse userResponse;

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    public void createUser() {
        UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
            .userEmail("abcAbc")
            .userName("Sharyf")
            .userPassword("blablablabla")
            .build();

        userResponse = userService.createUser(userSignUpRequest);
        Assertions.assertEquals(userResponse.getUserName(), userSignUpRequest.getUserName());
    }

    @Test
    @Order(2)
    public void updateUser() {
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
            .userName("Andrey")
            .userEmail("cba")
            .userPassword1("blablabla")
            .userPassword2("blablabla")
            .build();
        userService.updateUser(userUpdateRequest, userResponse.getUserId());
    }

    @Test
    @Order(3)
    public void deleteUser() {
        userService.delete(userResponse.getUserId());
    }
}
