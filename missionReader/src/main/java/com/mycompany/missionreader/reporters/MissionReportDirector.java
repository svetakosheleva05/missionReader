/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.reporters;

import com.mycompany.missionreader.Mission;

import java.util.Set;
/**
 *
 * @author svetl
 */
public class MissionReportDirector {
  
    private final Mission mission;
    private final Set<ReportSection> enabledSections;
    private MissionReportBuilder builder;
    
    public MissionReportDirector(Mission mission, Set<ReportSection> enabledSections, MissionReportBuilder builder) {
        this.mission = mission;
        this.enabledSections = enabledSections;
        this.builder = builder;
    }
    
    public Report construct() {
        if (enabledSections.contains(ReportSection.BASIC_INFO)) {
            builder.addBasicInfo();
        }
        if (enabledSections.contains(ReportSection.DAMAGE_COST)) {
            builder.addDamageCost();
        }
        if (enabledSections.contains(ReportSection.TARGET_CURSE)) {
            builder.addTargetCurse();
        }
        if (enabledSections.contains(ReportSection.PARTICIPANTS)) {
            builder.addParticipants();
        }
        if (enabledSections.contains(ReportSection.TECHNIQUES)) {
            builder.addTechniques();
        }
        if (enabledSections.contains(ReportSection.ECONOMIC)) {
            builder.addEconomicAssessment();
        }
        if (enabledSections.contains(ReportSection.CIVILIAN)) {
            builder.addCivilianImpact();
        }
        if (enabledSections.contains(ReportSection.ENEMY_ACTIVITY)) {
            builder.addEnemyActivity();
        }
        if (enabledSections.contains(ReportSection.ENVIRONMENT)) {
            builder.addEnvironmentConditions();
        }
        if (enabledSections.contains(ReportSection.TIMELINE)) {
            builder.addTimeline();
        }
        
        return builder.build();
    } 
}
