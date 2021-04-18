package com.example.demoCarsForSale.services;

import com.example.demoCarsForSale.pojo.User;
import com.example.demoCarsForSale.web.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.web.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.web.dto.response.UserExtraInfoResponse;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public interface UserService {

    List<UserExtraInfoResponse> findUsersExtraInfo(Pageable pageable);

    void delete(long id);

    UserResponse createUser(UserSignUpRequest userSignUpRequest);

    void updateUser(UserUpdateRequest userUpdateRequest, long userId);

    default boolean checkNewPassword(UserUpdateRequest userUpdateRequest) {
        return userUpdateRequest.getUserPassword1() != null &&
            userUpdateRequest.getUserPassword2() != null &&
            userUpdateRequest.getUserPassword1().equals(userUpdateRequest.getUserPassword2());
    }

    default void checkValidation(boolean isValid, Supplier<? extends RuntimeException> supplier) {
        if (!isValid) {
            throw supplier.get();
        }
    }


    List<User> testUser();

    default <T, R> R converter(T fromType, R toType, BiFunction<T, R, R> fun) {
        return fun.apply(fromType, toType);
    }
}
