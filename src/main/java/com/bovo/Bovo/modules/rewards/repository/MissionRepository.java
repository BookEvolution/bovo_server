package com.bovo.Bovo.modules.rewards.repository;

import com.bovo.Bovo.common.Mission;
import com.bovo.Bovo.common.MissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Integer> {
    Optional<Mission> findByMissionType(MissionType missionType);
}
