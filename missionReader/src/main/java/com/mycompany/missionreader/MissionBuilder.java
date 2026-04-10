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
public class MissionBuilder implements Builder {
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
    
    @Override
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
    
    @Override
    public Builder setMissionId(String missionId) {
        this.missionId = missionId;
        return this;
    }
    
    @Override
    public Builder setDate(LocalDate date) {
        this.date = date;
        return this;
    }
    
    @Override
    public Builder setLocation(String location) {
        this.location = location;
        return this;
    }
    
    @Override
    public Builder setOutcome(MissionOutcome outcome) {
        this.outcome = outcome;
        return this;
    }
    
    @Override
    public Builder setDamageCost(int damageCost) {
        this.damageCost = damageCost;
        return this;
    }
    
    @Override
    public Builder setTargetCurse(Curse curse) {
        this.targetCurse = curse;
        return this;
    }
    
    @Override
    public Builder setParticipants(List<Sorcerer> participants) {
        this.participants = new ArrayList<>(participants);
        return this;
    }
    
    @Override
    public Builder addParticipant(Sorcerer sorcerer) {
        this.participants.add(sorcerer);
        return this;
    }
    
    @Override
    public Builder setTechniqueUsed(List<Technique> techniques) {
        this.techniqueUsed = new ArrayList<>(techniques);
        return this;
    }
    
    @Override
    public Builder addTechnique(Technique technique) {
        this.techniqueUsed.add(technique);
        return this;
    }
    
    @Override
    public Builder setEconomicAssessment(EconomicAssessment assessment) {
        this.economicAssessment = assessment;
        return this;
    }
    
    @Override
    public Builder setCivilianImpact(CivilianImpact impact) {
        this.civilianImpact = impact;
        return this;
    }
    
    @Override
    public Builder setEnemyActivity(EnemyActivity activity) {
        this.enemyActivity = activity;
        return this;
    }
    
    @Override
    public Builder setEnvironmentConditions(EnvironmentConditions conditions) {
        this.environmentConditions = conditions;
        return this;
    }
    
    @Override
    public Builder setOperationTimelines(List<OperationTimeline> timeline) {
        this.operationTimelines = timeline;
        return this;
    }
    
    @Override
    public Builder setOtherData(HashMap<String, ArrayList<String>> otherData) {
        this.otherData = new HashMap<>(otherData);
        return this;
    }
    
    @Override
    public Builder addOtherData(String key, ArrayList<String> values) {
        this.otherData.put(key, values);
        return this;
    }
    
    @Override
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
