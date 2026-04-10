/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.reporters;

import java.util.List;
import java.util.Map;

/**
 *
 * @author svetl
 */

public class Report {
    private Map<String, String> basicInfo;  
    private String damageCost;               
    private String targetCurse;                 
    private List<String> participants;          
    private List<String> techniques;            
    private String economicAssessment;          
    private String civilianImpact;             
    private String enemyActivity;               
    private String environmentConditions;       
    private List<String> timeline;              

    public Map<String, String> getBasicInfo() {
        return basicInfo;
    }

    public String getDamageCost() {
        return damageCost;
    }

    public String getTargetCurse() {
        return targetCurse;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public List<String> getTechniques() {
        return techniques;
    }

    public String getEconomicAssessment() {
        return economicAssessment;
    }

    public String getCivilianImpact() {
        return civilianImpact;
    }

    public String getEnemyActivity() {
        return enemyActivity;
    }

    public String getEnvironmentConditions() {
        return environmentConditions;
    }

    public List<String> getTimeline() {
        return timeline;
    }

    public void setBasicInfo(Map<String, String> basicInfo) {
        this.basicInfo = basicInfo;
    }

    public void setDamageCost(String damageCost) {
        this.damageCost = damageCost;
    }

    public void setTargetCurse(String targetCurse) {
        this.targetCurse = targetCurse;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public void setTechniques(List<String> techniques) {
        this.techniques = techniques;
    }

    public void setEconomicAssessment(String economicAssessment) {
        this.economicAssessment = economicAssessment;
    }

    public void setCivilianImpact(String civilianImpact) {
        this.civilianImpact = civilianImpact;
    }

    public void setEnemyActivity(String enemyActivity) {
        this.enemyActivity = enemyActivity;
    }

    public void setEnvironmentConditions(String environmentConditions) {
        this.environmentConditions = environmentConditions;
    }

    public void setTimeline(List<String> timeline) {
        this.timeline = timeline;
    }
}
