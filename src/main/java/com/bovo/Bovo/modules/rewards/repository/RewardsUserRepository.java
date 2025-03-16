package com.bovo.Bovo.modules.rewards.repository;

import com.bovo.Bovo.common.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RewardsUserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findById(Integer userId);
}
