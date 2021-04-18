package com.example.demoCarsForSale.repository;

import com.example.demoCarsForSale.pojo.Ad;
import com.example.demoCarsForSale.pojo.User;
import com.example.demoCarsForSale.pojo.UserPhone;
import com.example.demoCarsForSale.web.dto.projection.UserExtraInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(@Param("email") String email);

    @EntityGraph(attributePaths = "ads")
    Slice<UserExtraInfo> findAllBy(Pageable pageable);

    @EntityGraph(attributePaths = "userPhones")
    Optional<User> findUserWithUserPhonesByUserId(@Param("userId") long userId);

    @EntityGraph(attributePaths = "ads")
    @Query("select  user FROM User user where user in :users")
    List<User> findUsersFetchingAds(List<User> users);

    @EntityGraph(attributePaths = "userPhones")
    @Query("select  user FROM User user where user in :users")
    List<User> findUsersFetchingPhones(List<User> users);
}
