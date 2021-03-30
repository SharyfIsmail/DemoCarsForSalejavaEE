package adServiceTest;

import com.example.demoCarsForSale.controllers.dto.request.AdRequest;
import com.example.demoCarsForSale.controllers.dto.request.PhoneRequest;
import com.example.demoCarsForSale.controllers.dto.request.PicRequest;
import com.example.demoCarsForSale.controllers.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.controllers.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.controllers.dto.response.UserResponse;
import com.example.demoCarsForSale.dao.model.Condition;
import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.services.impl.AdServiceImpl;
import com.example.demoCarsForSale.services.impl.PicServiceImpl;
import com.example.demoCarsForSale.services.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAd {
    public static final UserService USER_SERVICE = new UserServiceImpl();
    public static final PicServiceImpl PIC_SERVICE = new PicServiceImpl();
    public static final AdServiceImpl adService = new AdServiceImpl();

    public static final UserSignUpRequest USER_SIGN_UP_REQUEST = new UserSignUpRequest();
    public static final AdRequest adRequest = new AdRequest();
    public static final PhoneRequest phoneRequest1 = new PhoneRequest();
    public static final PhoneRequest phoneRequest2 = new PhoneRequest();
    public static final PicRequest pic1 = new PicRequest();

    public static UserResponse userResponse;
    public static AdDetailedResponse adDetailedResponse;

    static {

        phoneRequest1.setPhone("+375293738074");
        phoneRequest2.setPhone("+375293738076");

        pic1.setPic("www.porno.hub.com");

        adRequest.setBrand("cooper");
        adRequest.setCondition(Condition.NEW);
        adRequest.setPhones(Arrays.asList(
            phoneRequest1, phoneRequest2));
        adRequest.setPics(Collections.singletonList(pic1));
        adRequest.setEngineCapacity(12);
        adRequest.setMileAge(1222);
        adRequest.setYear(2011);
        adRequest.setModel("S");

        USER_SIGN_UP_REQUEST.setUserEmail("Fucks@.com");
        USER_SIGN_UP_REQUEST.setUserName("Andrey");
        USER_SIGN_UP_REQUEST.setUserPassword("ABS");
    }

    @BeforeAll
    public static void createUserCreateAd() {
        userResponse = USER_SERVICE.createUser(USER_SIGN_UP_REQUEST);
        assertEquals(userResponse.getUserName(), USER_SIGN_UP_REQUEST.getUserName());

        adDetailedResponse = adService.createAd(adRequest, userResponse.getUserId());
    }

    @Test
    public void getAd() {
        AdDetailedResponse adGet = adService.getDetailedInfoAboutAd(adDetailedResponse.getAdId());
        assertEquals(adGet.getAdId(), adDetailedResponse.getAdId());
    }

//    @Test
//    public void deletePic() {
//        PIC_SERVICE.delete(7, 7);
//    }

    @AfterAll
    public static void deleteAd() {
        adService.deleteAd(adDetailedResponse.getAdId(), userResponse.getUserId());
        USER_SERVICE.delete(userResponse.getUserId());
    }
}
