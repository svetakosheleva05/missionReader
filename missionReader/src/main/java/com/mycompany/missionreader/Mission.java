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
public class Mission {
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
    
    public Mission(String missionId, LocalDate date, String location, MissionOutcome outcome) {
        this.missionId = missionId;
        this.date = date;
        this.location = location;
        this.outcome = outcome;
    }

    public String getMissionId() {
        return missionId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public MissionOutcome getOutcome() {
        return outcome;
    }

    public int getDamageCost() {
        return damageCost;
    }

    public Curse getTargetCurse() {
        return targetCurse;
    }

    public List<Sorcerer> getParticipants() {
        return participants;
    }

    public List<Technique> getTechniqueUsed() {
        return techniqueUsed;
    }

    public EconomicAssessment getEconomicAssessment() {
        return economicAssessment;
    }

    public CivilianImpact getCivilianImpact() {
        return civilianImpact;
    }

    public EnemyActivity getEnemyActivity() {
        return enemyActivity;
    }

    public EnvironmentConditions getEnvironmentConditions() {
        return environmentConditions;
    }

    public List<OperationTimeline> getOperationTimelinesS() {
        return operationTimelines;
    }

    public HashMap<String, ArrayList<String>> getOtherData() {
        return otherData;
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

    public void setTargetCurse(Curse targetCurse) {
        this.targetCurse = targetCurse;
    }

    public void setParticipants(List<Sorcerer> participants) {
        this.participants = participants;
    }

    public void setTechniqueUsed(List<Technique> techniqueUsed) {
        this.techniqueUsed = techniqueUsed;
    }

    public void setEconomicAssessment(EconomicAssessment economicAssessment) {
        this.economicAssessment = economicAssessment;
    }

    public void setCivilianImpact(CivilianImpact civilianImpact) {
        this.civilianImpact = civilianImpact;
    }

    public void setEnemyActivity(EnemyActivity enemyActivity) {
        this.enemyActivity = enemyActivity;
    }

    public void setEnvironmentConditions(EnvironmentConditions environmentConditions) {
        this.environmentConditions = environmentConditions;
    }

    public void setOperationTimelines(List<OperationTimeline> operationTimelines) {
        this.operationTimelines = operationTimelines;
    }

    public void setOtherData(HashMap<String, ArrayList<String>> otherData) {
        this.otherData = otherData;
    }
}
