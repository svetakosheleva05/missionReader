/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.missionreader.reporters;

/**
 *
 * @author svetl
 */
public interface ReportBuilder {
    MissionReportBuilder addBasicInfo();
    MissionReportBuilder addDamageCost();
    MissionReportBuilder addTargetCurse();
    MissionReportBuilder addParticipants();
    MissionReportBuilder addTechniques();
    MissionReportBuilder addEconomicAssessment();
    MissionReportBuilder addCivilianImpact();
    MissionReportBuilder addEnemyActivity();
    MissionReportBuilder addEnvironmentConditions();
    MissionReportBuilder addTimeline();
    Report build();
}
