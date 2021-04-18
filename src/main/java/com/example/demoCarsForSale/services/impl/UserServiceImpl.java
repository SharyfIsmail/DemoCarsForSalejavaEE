package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.exceptions.EntityNotFoundException;
import com.example.demoCarsForSale.pojo.User;
import com.example.demoCarsForSale.repository.UserRepository;
import com.example.demoCarsForSale.services.UserService;
import com.example.demoCarsForSale.services.mapper.UserResponseRequestMapper;
import com.example.demoCarsForSale.web.dto.projection.UserExtraInfo;
import com.example.demoCarsForSale.web.dto.request.UserSignUpRequest;
import com.example.demoCarsForSale.web.dto.request.UserUpdateRequest;
import com.example.demoCarsForSale.web.dto.response.UserExtraInfoResponse;
import com.example.demoCarsForSale.web.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<UserExtraInfoResponse> findUsersExtraInfo(Pageable pageable) {
        List<UserExtraInfo> userExtraInfos = userRepository.findAllBy(pageable).getContent();

        return UserResponseRequestMapper.toExtrInfoResponses(userExtraInfos);
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public UserResponse createUser(UserSignUpRequest userSignUpRequest) {
        userSignUpRequest.setUserPassword(passwordEncoder().encode(userSignUpRequest.getUserPassword()));

        checkValidation(!userRepository.existsByEmail(userSignUpRequest.getUserEmail()),
            () -> new BadRequestException(userSignUpRequest.getUserEmail() + " already exists"));

        User userEntity = userRepository.save(UserResponseRequestMapper
            .toUser(userSignUpRequest));

        return UserResponseRequestMapper.toResponse(userEntity);
    }

    @Transactional
    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest, long userId) {
        checkValidation(checkNewPassword(userUpdateRequest),
            () -> new BadRequestException("Passwords don't match"));

        User user = userRepository.findById(userId).orElseThrow(
            () -> new EntityNotFoundException("Oops user was grabbed by aliens"));

        userRepository.save(converter(userUpdateRequest, user,
            (fromType, toType) -> {
                toType.setEmail(fromType.getUserEmail());
                toType.setName(fromType.getUserName());
                toType.setPassword(passwordEncoder().encode(fromType.getUserPassword1()));
                return toType;
            }));
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> testUser() {
        List<User> users = userRepository.findAll();
        users = userRepository.findUsersFetchingAds(users);
        users = userRepository.findUsersFetchingPhones(users);

        return users;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Not found"));
    }
}
