/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.missionreader;

import com.mycompany.missionreader.enams.MissionOutcome;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author svetl
 */
public interface Builder {

    Builder setMissionId(String missionId);
    
    Builder setDate(LocalDate date);
    
    Builder setLocation(String location);
    
    Builder setOutcome(MissionOutcome outcome);
    
    Builder setDamageCost(int damageCost);
    
    Builder setTargetCurse(Curse curse);
    
    Builder setParticipants(List<Sorcerer> participants);
    
    Builder addParticipant(Sorcerer sorcerer);
    
    Builder setTechniqueUsed(List<Technique> techniques);
    
    Builder addTechnique(Technique technique);
    
    Builder setEconomicAssessment(EconomicAssessment assessment);
    
    Builder setCivilianImpact(CivilianImpact impact);
    
    Builder setEnemyActivity(EnemyActivity activity);
    
    Builder setEnvironmentConditions(EnvironmentConditions conditions);
    
    Builder setOperationTimelines(List<OperationTimeline> timelines);
    
    Builder setOtherData(HashMap<String, ArrayList<String>> otherData);
    
    Builder addOtherData(String key, ArrayList<String> values);
    
    Mission build();
    
    void reset();
}
