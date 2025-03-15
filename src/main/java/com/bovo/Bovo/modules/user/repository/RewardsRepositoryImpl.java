package com.bovo.Bovo.modules.user.repository;

import com.bovo.Bovo.common.Medal;
import com.bovo.Bovo.common.Mission;
import com.bovo.Bovo.common.MyMissionProgress;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@Transactional
public class RewardsRepositoryImpl implements RewardsRepository {
    private final EntityManager em;

    public RewardsRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Medal saveMedal(Medal medal) {
        em.persist(medal);
        return medal;
    }

    public List<Mission> findAllMissions() {
        return em.createQuery("SELECT m FROM Mission m", Mission.class)
                .getResultList();
    }

    @Override
    public MyMissionProgress saveMyMissionProgress(MyMissionProgress myMissionProgress) {
        em.persist(myMissionProgress);
        return myMissionProgress;
    }
}
