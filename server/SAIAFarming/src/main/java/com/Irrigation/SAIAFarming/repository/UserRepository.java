package com.Irrigation.SAIAFarming.repository;

import com.Irrigation.SAIAFarming.entity.usermanagement.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserRegistration, String> {
    UserRegistration findBymobilenum(String mobilenum);
    UserRegistration findById(String id);

}
