package controller_test;

import com.example.demoCarsForSale.BootApplication;
import com.example.demoCarsForSale.security.TokenUtil;
import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.web.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.web.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.web.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.web.dto.response.JwtResponse;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BootApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private UserResponse userResponse;
    private static JwtResponse response;
    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
            .userEmail("fma.com")
            .userName("Sharyf")
            .userPassword("blablablabla")
            .build();

        userResponse = userService.createUser(userSignUpRequest);
    }

    @Test
    public void logIn() throws Exception {
        UserLogInRequest userLogInRequest = UserLogInRequest.builder()
            .userEmail("fma.com")
            .userPassword("blablablabla")
            .build();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/login")
            .content(MAPPER.writeValueAsString(userLogInRequest))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andReturn();

        response = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), JwtResponse.class);
        Assertions.assertEquals(TokenUtil.getEmailFromToken(response.getToken()), userLogInRequest.getUserEmail());
    }

    @Test
    public void failedSignUp() throws Exception {
        UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
            .userEmail("xandrey95xlive.com")
            .userName("Sharyf")
            .userPassword("blablablabla")
            .build();

        mockMvc.perform(post("/api/v1/auth/signup")
            .content(MAPPER.writeValueAsString(userSignUpRequest))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateSignedUser() throws Exception {
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
            .userEmail("fma.com")
            .userName("Andrey")
            .userPassword1("blablablabla")
            .userPassword2("blablablabla")
            .build();

        mockMvc.perform(put("/api/v1/users/update")
            .content(MAPPER.writeValueAsString(userUpdateRequest))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken()))
            .andExpect(status().isOk());
    }

    @AfterEach
    public void cleanup() {
        userService.delete(userResponse.getUserId());
    }
}
