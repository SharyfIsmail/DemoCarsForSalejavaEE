package controller_test;

import com.example.demoCarsForSale.BootApplication;
import com.example.demoCarsForSale.pojo.Condition;
import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.services.PicService;
import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.web.dto.request.AdRequest;
import com.example.demoCarsForSale.web.dto.request.PhoneRequest;
import com.example.demoCarsForSale.web.dto.request.PicRequest;
import com.example.demoCarsForSale.web.dto.request.UserLogInRequest;
import com.example.demoCarsForSale.web.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.web.dto.response.AdDetailedResponse;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@SpringBootTest(classes = BootApplication.class)
@AutoConfigureMockMvc
public class AdController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private PicService picService;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static UserResponse userResponse;
    private static JwtResponse jwtResponse;
    private static AdDetailedResponse adDetailedResponse;

    @BeforeEach
    public void setUp() throws Exception {
        UserSignUpRequest userSignUpRequest = UserSignUpRequest.builder()
            .userEmail("fma.com")
            .userName("Sharyf")
            .userPassword("blablablabla")
            .build();
        UserLogInRequest userLogInRequest = UserLogInRequest.builder()
            .userEmail("fma.com")
            .userPassword("blablablabla")
            .build();
        userResponse = userService.createUser(userSignUpRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
            .content(MAPPER.writeValueAsString(userLogInRequest))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        jwtResponse = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), JwtResponse.class);
    }

    @Test
    public void createAd() throws Exception {
        AdRequest adRequest = AdRequest.builder()
            .condition(Condition.NEW)
            .brand("Cooper")
            .engineCapacity(123)
            .mileAge(123)
            .model("S")
            .year(2011)
            .phones(Arrays.asList(PhoneRequest.builder().phone("+375293738074").build(),
                PhoneRequest.builder().phone("+375293738077").build()))
            .pics(Arrays.asList(PicRequest.builder().pic("http//:firstPicture").build(),
                PicRequest.builder().pic("http//:secondPicture").build()))
            .power(123)
            .build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ads")
            .content(MAPPER.writeValueAsString(adRequest))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtResponse.getToken()))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();

        adDetailedResponse = MAPPER.readValue(mvcResult.getResponse().getContentAsString(), AdDetailedResponse.class);

        Assertions.assertEquals(adDetailedResponse.getBrand(), adRequest.getBrand());
        Assertions.assertEquals(adDetailedResponse.getCondition(), adRequest.getCondition());
        Assertions.assertEquals(adDetailedResponse.getUserName(), userResponse.getUserName());
        Assertions.assertEquals(Integer.valueOf(adDetailedResponse.getPower()), adRequest.getPower());
        Assertions.assertEquals(Integer.valueOf(adDetailedResponse.getYear()), adRequest.getYear());
        Assertions.assertEquals(adDetailedResponse.getModel(), adRequest.getModel());
    }

    @AfterEach
    public void cleanUp() {
        userService.delete(userResponse.getUserId());
    }
}
