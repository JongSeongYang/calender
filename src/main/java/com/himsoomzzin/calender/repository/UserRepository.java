package com.himsoomzzin.calender.repository;

import com.himsoomzzin.calender.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityById(Long id);
}
