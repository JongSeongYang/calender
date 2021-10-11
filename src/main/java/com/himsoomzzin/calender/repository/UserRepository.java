package com.himsoomzzin.calender.repository;

import com.himsoomzzin.calender.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityById(Long id);

    Optional<UserEntity> findUserEntityByUserId(String loginId);

    Optional<UserEntity> findUserEntityByPhoneNumber(String phoneNumber);
}
