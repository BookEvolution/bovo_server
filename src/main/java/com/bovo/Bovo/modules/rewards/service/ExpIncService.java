package com.bovo.Bovo.modules.rewards.service;

import com.bovo.Bovo.common.Mission;
import com.bovo.Bovo.common.MyMissionProgress;
import com.bovo.Bovo.common.Users;

public interface ExpIncService {

    void updateExp(Integer userId, Integer missionId);

    void updateMissionProgress(MyMissionProgress progress, Mission mission);

    void updateUserExp(Users user, int expGained);

    void updateLevel(Users user);

    int getLevelUpThreshold(int level);

    void completeQuest(Integer userId, Integer missionId);
}