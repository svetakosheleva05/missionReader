/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class MissionBuilder{
    private String missionId;
    private LocalDate date;
    private String location;
    private MissionOutcome outcome;
    private int damageCost;
    private Curse targetCurse;
    private List<Sorcerer> participants;
    private List<Technique> techniqueUsed;
    private EconomicAssessment economicAssessment;
    private CivilianImpact civilianImpact;
    private EnemyActivity enemyActivity;
    private EnvironmentConditions environmentConditions;
    private List<OperationTimeline> operationTimelines;
    private HashMap<String, ArrayList<String>> otherData;
    
    public MissionBuilder() {
        reset();
    }
    
    public void reset() {
        this.missionId = null;
        this.date = null;
        this.location = null;
        this.outcome = null;
        this.damageCost = 0;
        this.targetCurse = null;
        this.participants = new ArrayList<>();
        this.techniqueUsed = new ArrayList<>();
        this.economicAssessment = null;
        this.civilianImpact = null;
        this.enemyActivity = null;
        this.environmentConditions = null;
        this.operationTimelines = new ArrayList<>();
        this.otherData = new HashMap<>();
    }
    
    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setOutcome(MissionOutcome outcome) {
        this.outcome = outcome;
    }
    
    public void setDamageCost(int damageCost) {
        this.damageCost = damageCost;
    }
    
    public void setTargetCurse(Curse curse) {
        this.targetCurse = curse;
    }
    
    public void setParticipants(List<Sorcerer> participants) {
        this.participants = new ArrayList<>(participants);
    }
    
    public void addParticipant(Sorcerer sorcerer) {
        this.participants.add(sorcerer);
    }

    public void setTechniqueUsed(List<Technique> techniques) {
        this.techniqueUsed = new ArrayList<>(techniques);
    }
    
    public void addTechnique(Technique technique) {
        this.techniqueUsed.add(technique);
    }
    
    public void setEconomicAssessment(EconomicAssessment assessment) {
        this.economicAssessment = assessment;
    }
    
    public void setCivilianImpact(CivilianImpact impact) {
        this.civilianImpact = impact;
    }
    
    public void setEnemyActivity(EnemyActivity activity) {
        this.enemyActivity = activity;
    }
    
    public void setEnvironmentConditions(EnvironmentConditions conditions) {
        this.environmentConditions = conditions;
    }
    
    public void setOperationTimelines(List<OperationTimeline> timeline) {
        this.operationTimelines = timeline;
    }
    
    public void setOtherData(HashMap<String, ArrayList<String>> otherData) {
        this.otherData = new HashMap<>(otherData);
    }
    
    public void addOtherData(String key, ArrayList<String> values) {
        this.otherData.put(key, values);
    }
    
    public Mission build() {
        Mission mission = new Mission(missionId, date, location, outcome);
        
        mission.setDamageCost(this.damageCost);
        mission.setTargetCurse(this.targetCurse);
        mission.setParticipants(this.participants);
        mission.setTechniqueUsed(this.techniqueUsed);
        mission.setEconomicAssessment(this.economicAssessment);
        mission.setCivilianImpact(this.civilianImpact);
        mission.setEnemyActivity(this.enemyActivity);
        mission.setEnvironmentConditions(this.environmentConditions);
        mission.setOperationTimelines(this.operationTimelines);
        mission.setOtherData(this.otherData);
        reset();
        return mission;
    }

}
