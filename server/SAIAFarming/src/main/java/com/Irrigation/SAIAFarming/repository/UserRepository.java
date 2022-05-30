package com.Irrigation.SAIAFarming.repository;

import com.Irrigation.SAIAFarming.entity.usermanagement.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserRegistration, String> {
    UserRegistration findBymobilenum(String mobilenum);
    UserRegistration findByid(String id);

}
