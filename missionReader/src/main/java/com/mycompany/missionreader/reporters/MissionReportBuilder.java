/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.reporters;

import com.mycompany.missionreader.CivilianImpact;
import com.mycompany.missionreader.EconomicAssessment;
import com.mycompany.missionreader.EnemyActivity;
import com.mycompany.missionreader.EnvironmentConditions;
import com.mycompany.missionreader.Mission;
import com.mycompany.missionreader.OperationTimeline;
import com.mycompany.missionreader.Sorcerer;
import com.mycompany.missionreader.Technique;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author svetl
 */
public class MissionReportBuilder implements ReportBuilder {
    private Report data = new Report();
    private Mission mission;
    
    public MissionReportBuilder(Mission mission) {
        this.mission = mission;
    }
    
    private String nullToZatichka(Object value) {
        return value == null ? "Не указано" : value.toString();
    }
    
    @Override
    public MissionReportBuilder addBasicInfo() {
        Map<String, String> basic = new HashMap<>();
        basic.put("ID миссии", nullToZatichka(mission.getMissionId()));
        basic.put("Дата", nullToZatichka(mission.getDate()));
        basic.put("Локация", nullToZatichka(mission.getLocation()));
        basic.put("Исход", nullToZatichka(mission.getOutcome()));
        data.setBasicInfo(basic);
        return this;
    }
    
    @Override
    public MissionReportBuilder addDamageCost() {
        String cost = String.valueOf(mission.getDamageCost());
        data.setDamageCost(cost);
        return this;
    }
    
    @Override
    public MissionReportBuilder addTargetCurse() {
        String curse = (mission.getTargetCurse() == null)
            ? "Не указано"
            : mission.getTargetCurse().toString();
        data.setTargetCurse(curse);
        return this;
    }
    
    @Override
    public MissionReportBuilder addParticipants() {
        List<Sorcerer> participants = mission.getParticipants();
        if (participants == null) {
            data.setParticipants(null);
        } else {
            List<String> sorcerers = new ArrayList<>();
            for (Sorcerer sorcerer : participants) {
                sorcerers.add(sorcerer.toString());
            }
            data.setParticipants(sorcerers);
        }
        return this;
    }
    
    @Override
    public MissionReportBuilder addTechniques() {
        List<Technique> techniques = mission.getTechniqueUsed();
        if (techniques == null) {
            data.setTechniques(null);
        } else {
            List<String> techniqueStrings = new ArrayList<>();
            for (Technique technique : techniques) {
                techniqueStrings.add(technique.toString());
            }
            data.setTechniques(techniqueStrings);
        }
        return this;
    }
    
    @Override
    public MissionReportBuilder addEconomicAssessment() {
        EconomicAssessment economicAssessment = mission.getEconomicAssessment();
        data.setEconomicAssessment(economicAssessment == null ? null : economicAssessment.toString());
        return this;
    }
    
    @Override
    public MissionReportBuilder addCivilianImpact() {
        CivilianImpact civilianImpact = mission.getCivilianImpact();
        data.setCivilianImpact(civilianImpact == null ? null : civilianImpact.toString());
        return this;
    }
    
    @Override
    public MissionReportBuilder addEnemyActivity() {
        EnemyActivity enemyActivity = mission.getEnemyActivity();
        data.setEnemyActivity(enemyActivity == null ? null : enemyActivity.toString());
        return this;
    }
    
    @Override
    public MissionReportBuilder addEnvironmentConditions() {
        EnvironmentConditions environmentConditions = mission.getEnvironmentConditions();
        data.setEnvironmentConditions(environmentConditions == null ? null : environmentConditions.toString());
        return this;
    }
    
    @Override
    public MissionReportBuilder addTimeline() {
        List<OperationTimeline> timeline = mission.getOperationTimelinesS();
        if (timeline == null) {
            data.setTimeline(null);
        } else {
            List<String> operationTimelineStrings = new ArrayList<>();
            for (OperationTimeline operationTimeline : timeline) {
                operationTimelineStrings.add(operationTimeline.toString());
            }
            data.setTimeline(operationTimelineStrings);
        }
        return this;
    }
    
    @Override
    public Report build() {
        return data;
    }
}
