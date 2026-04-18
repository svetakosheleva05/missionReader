/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.reporters;

import com.mycompany.missionreader.Mission;

/**
 *
 * @author svetl
 */
public class FullReportStrategy implements ReportStrategy {
    @Override
    public Report fillReport(Mission mission) {
        
        Report report = new Report();
        
        report.setBasicInfo(ValueFormatter.basicInfo(mission));
        report.setDamageCost(ValueFormatter.costToString(mission.getDamageCost()));
        report.setTargetCurse(ValueFormatter.orNot(mission.getTargetCurse()));
        report.setParticipants(ValueFormatter.listOrEmpty(mission.getParticipants()));
        report.setTechniques(ValueFormatter.listOrEmpty(mission.getTechniqueUsed()));
        report.setEconomicAssessment(ValueFormatter.orNot(mission.getEconomicAssessment()));
        report.setCivilianImpact(ValueFormatter.orNot(mission.getCivilianImpact()));
        report.setEnemyActivity(ValueFormatter.orNot(mission.getEnemyActivity()));
        report.setEnvironmentConditions(ValueFormatter.orNot(mission.getEnvironmentConditions()));
        report.setTimeline(ValueFormatter.listOrEmpty(mission.getOperationTimelinesS()));
        
        return report;
    }
}
