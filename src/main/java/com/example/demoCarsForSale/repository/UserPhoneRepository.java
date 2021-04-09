package com.example.demoCarsForSale.repository;

import com.example.demoCarsForSale.pojo.UserPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPhoneRepository extends JpaRepository<UserPhone, Long> {
}
