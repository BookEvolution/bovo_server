package com.bovo.Bovo.modules.user.repository;

import com.bovo.Bovo.common.Medal;
import com.bovo.Bovo.common.Mission;
import com.bovo.Bovo.common.MyMissionProgress;

import java.util.List;

public interface RewardsRepository {
    Medal saveMedal(Medal medal);

    List<Mission> findAllMissions();

    MyMissionProgress saveMyMissionProgress(MyMissionProgress myMissionProgress);
}
