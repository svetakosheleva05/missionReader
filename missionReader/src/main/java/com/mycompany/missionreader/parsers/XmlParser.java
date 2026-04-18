/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mycompany.missionreader.CivilianImpact;
import com.mycompany.missionreader.Curse;
import com.mycompany.missionreader.CurseRegister;
import com.mycompany.missionreader.EconomicAssessment;
import com.mycompany.missionreader.EnemyActivity;
import com.mycompany.missionreader.EnvironmentConditions;
import com.mycompany.missionreader.Mission;
import com.mycompany.missionreader.MissionBuilder;
import com.mycompany.missionreader.MissionStorage;
import com.mycompany.missionreader.OperationTimeline;
import com.mycompany.missionreader.Sorcerer;
import com.mycompany.missionreader.SorcererRegister;
import com.mycompany.missionreader.Technique;
import com.mycompany.missionreader.enams.EnemyMobility;
import com.mycompany.missionreader.enams.EscalationRisk;
import com.mycompany.missionreader.enams.MissionOutcome;
import com.mycompany.missionreader.enams.PublicExposureRisk;
import com.mycompany.missionreader.enams.Visibility;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author svetl
 */
public class XmlParser implements MissionParser {
    
    private SorcererRegister sorcererRegister;
    private CurseRegister curseRegister;
    private MissionStorage missionStorage;
    private MissionBuilder builder;
    
    public XmlParser(SorcererRegister sorcererRegister, CurseRegister curseRegister, MissionStorage missionStorage, MissionBuilder builder) {
        this.sorcererRegister = sorcererRegister;
        this.curseRegister = curseRegister;
        this.missionStorage = missionStorage;
        this.builder = builder;
    }
    
    @Override
    public Mission parse(File file) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(file);
            
            if (jsonNode.has("missionId") && jsonNode.get("missionId") != null) {
                builder.setMissionId(jsonNode.get("missionId").asText());
            }
            if (jsonNode.has("date") && jsonNode.get("date") != null) {
                builder.setDate(LocalDate.parse(jsonNode.get("date").asText()));
            }
            if (jsonNode.has("location") && jsonNode.get("location") != null) {
                builder.setLocation(jsonNode.get("location").asText());
            }
            if (jsonNode.has("outcome") && jsonNode.get("outcome") != null) {
                builder.setOutcome(MissionOutcome.fromStringToEnum(jsonNode.get("outcome").asText()));
            }
            if (jsonNode.has("damageCost") && jsonNode.get("damageCost") != null) {
                builder.setDamageCost(jsonNode.get("damageCost").asInt());
            }
            
            if (jsonNode.has("curse") && jsonNode.get("curse") != null && !jsonNode.get("curse").isNull()) {
                JsonNode curseNode = jsonNode.get("curse");
                String curseName = curseNode.has("name") && curseNode.get("name") != null ? curseNode.get("name").asText() : null;
                String threatLevel = curseNode.has("threatLevel") && curseNode.get("threatLevel") != null ? curseNode.get("threatLevel").asText() : null;
                if (curseName != null) {
                    builder.setTargetCurse(curseRegister.getOrCreate(new Curse(curseName, threatLevel)));
                }
            }
            
            ArrayList<Sorcerer> sorcerers = parseSorcerers(jsonNode.get("sorcerers"));
            if (!sorcerers.isEmpty()) {
                builder.setParticipants(sorcerers);
            }
            
            ArrayList<Technique> techniques = parseTechniques(jsonNode.get("techniques"));
            if (!techniques.isEmpty()) {
                builder.setTechniqueUsed(techniques);
            }
            
            if (jsonNode.has("economicAssessment") && jsonNode.get("economicAssessment") != null && !jsonNode.get("economicAssessment").isNull()) {
                JsonNode econNode = jsonNode.get("economicAssessment");
                EconomicAssessment assessment = new EconomicAssessment(0, 0, 0, 0, 0, false);
                if (econNode.has("totalDamageCost")) assessment.setTotalDamageCost(econNode.get("totalDamageCost").asInt());
                if (econNode.has("infrastructureDamage")) assessment.setInfrastructureDamage(econNode.get("infrastructureDamage").asInt());
                if (econNode.has("commercialDamage")) assessment.setCommercialDamage(econNode.get("commercialDamage").asInt());
                if (econNode.has("transportDamage")) assessment.setTransportDamage(econNode.get("transportDamage").asInt());
                if (econNode.has("recoveryEstimateDays")) assessment.setRecoveryEstimateDays(econNode.get("recoveryEstimateDays").asInt());
                if (econNode.has("insuranceCovered")) assessment.setInsuranceCovered(econNode.get("insuranceCovered").asBoolean());
                builder.setEconomicAssessment(assessment);
            }
            
            if (jsonNode.has("civilianImpact") && jsonNode.get("civilianImpact") != null && !jsonNode.get("civilianImpact").isNull()) {
                JsonNode civilianNode = jsonNode.get("civilianImpact");
                CivilianImpact impact = new CivilianImpact();
                if (civilianNode.has("evacuated")) impact.setEvacuated(civilianNode.get("evacuated").asInt());
                if (civilianNode.has("injured")) impact.setInjured(civilianNode.get("injured").asInt());
                if (civilianNode.has("missing")) impact.setMissing(civilianNode.get("missing").asInt());
                if (civilianNode.has("publicExposureRisk")) {
                    impact.setPublicExposureRisk(PublicExposureRisk.fromStringToEnum(civilianNode.get("publicExposureRisk").asText()));
                }
                builder.setCivilianImpact(impact);
            }
            
            if (jsonNode.has("enemyActivity") && jsonNode.get("enemyActivity") != null && !jsonNode.get("enemyActivity").isNull()) {
                JsonNode enemyNode = jsonNode.get("enemyActivity");
                EnemyActivity activity = new EnemyActivity();
                if (enemyNode.has("behaviorType")) activity.setBehaviorType(enemyNode.get("behaviorType").asText());
                if (enemyNode.has("mobility")) activity.setMobility(EnemyMobility.fromStringToEnum(enemyNode.get("mobility").asText()));
                if (enemyNode.has("escalationRisk")) activity.setEscalationRisk(EscalationRisk.fromStringToEnum(enemyNode.get("escalationRisk").asText()));
                
                if (enemyNode.has("targetPriority")) {
                    ArrayList<String> targets = parseStringList(enemyNode.get("targetPriority"), "targetPriority");
                    if (!targets.isEmpty()) 
                        activity.setTargetPriority(targets);
                }
                if (enemyNode.has("attackPatterns")) {
                    ArrayList<String> patterns = parseStringList(enemyNode.get("attackPatterns"), "pattern");
                    if (!patterns.isEmpty()) 
                        activity.setAttackPatterns(patterns);
                }
                builder.setEnemyActivity(activity);
            }
            
            if (jsonNode.has("environmentConditions") && jsonNode.get("environmentConditions") != null && !jsonNode.get("environmentConditions").isNull()) {
                JsonNode envNode = jsonNode.get("environmentConditions");
                EnvironmentConditions conditions = new EnvironmentConditions();
                if (envNode.has("weather")) conditions.setWeather(envNode.get("weather").asText());
                if (envNode.has("timeOfDay")) conditions.setTimeOfDay(envNode.get("timeOfDay").asText());
                if (envNode.has("visibility")) conditions.setVisibility(Visibility.fromStringToEnum(envNode.get("visibility").asText()));
                if (envNode.has("cursedEnergyDensity")) conditions.setCursedEnergyDensity(envNode.get("cursedEnergyDensity").asInt());
                builder.setEnvironmentConditions(conditions);
            }
            
            if (jsonNode.has("operationTimeline") && jsonNode.get("operationTimeline") != null && !jsonNode.get("operationTimeline").isNull()) {
                List<OperationTimeline> timelineList = new ArrayList<>();
                JsonNode timelineArray = jsonNode.get("operationTimeline");
                if (timelineArray.has("event")) {
                    JsonNode events = timelineArray.get("event");
                    if (events.isArray()) {
                        for (JsonNode event : events) {
                            OperationTimeline timeline = parseTimelineEvent(event);
                            if (timeline != null) 
                                timelineList.add(timeline);
                        }
                    } else if (events.isObject()) {
                        OperationTimeline timeline = parseTimelineEvent(events);
                        if (timeline != null) 
                            timelineList.add(timeline);
                    }
                }
                if (!timelineList.isEmpty()) 
                    builder.setOperationTimelines(timelineList);
            }
            
            return builder.build();
            
        } catch (IOException e) {
            System.err.println("Ошибка при парсинге XML: " + e.getMessage());
            return null;
        }
    }
    
    private OperationTimeline parseTimelineEvent(JsonNode eventNode) {
        if (eventNode == null || eventNode.isNull()) return null;
        OperationTimeline timeline = new OperationTimeline();
        if (eventNode.has("timestamp")) timeline.setTimestamp(LocalDateTime.parse(eventNode.get("timestamp").asText()));
        if (eventNode.has("type")) timeline.setType(eventNode.get("type").asText());
        if (eventNode.has("description")) timeline.setDescription(eventNode.get("description").asText());
        return timeline;
    }
    
    private ArrayList<String> parseStringList(JsonNode parentNode, String childName) {
        ArrayList<String> result = new ArrayList<>();
        if (parentNode == null || parentNode.isNull()) 
            return result;
        JsonNode items = parentNode.get(childName);
        if (items == null || items.isNull()) 
            return result;
        if (items.isArray()) {
            for (JsonNode item : items) {
                result.add(item.asText());
            }
        } else {
            result.add(items.asText());
        }
        return result;
    }
    
    private ArrayList<Sorcerer> parseSorcerers(JsonNode sorcerersNode) {
        ArrayList<Sorcerer> result = new ArrayList<>();
        if (sorcerersNode == null || sorcerersNode.isNull()) return result;
        if (!sorcerersNode.has("sorcerer")) return result;
        JsonNode sorcererArray = sorcerersNode.get("sorcerer");
        if (sorcererArray == null || sorcererArray.isNull()) return result;
        if (sorcererArray.isArray()) {
            for (JsonNode sorcererNode : sorcererArray) {
                Sorcerer sorcerer = parseSingleSorcerer(sorcererNode);
                if (sorcerer != null) result.add(sorcerer);
            }
        } else {
            Sorcerer sorcerer = parseSingleSorcerer(sorcererArray);
            if (sorcerer != null) result.add(sorcerer);
        }
        return result;
    }
    
    private Sorcerer parseSingleSorcerer(JsonNode sorcererNode) {
        if (sorcererNode == null || sorcererNode.isNull()) return null;
        String name = sorcererNode.has("name") && sorcererNode.get("name") != null ? sorcererNode.get("name").asText() : null;
        String rank = sorcererNode.has("rank") && sorcererNode.get("rank") != null ? sorcererNode.get("rank").asText() : null;
        if (name == null) return null;
        return sorcererRegister.getOrCreate(new Sorcerer(name, rank));
    }
    
    private ArrayList<Technique> parseTechniques(JsonNode techniquesNode) {
        ArrayList<Technique> result = new ArrayList<>();
        if (techniquesNode == null || techniquesNode.isNull()) return result;
        if (!techniquesNode.has("technique")) return result;
        JsonNode techniqueArray = techniquesNode.get("technique");
        if (techniqueArray == null || techniqueArray.isNull()) return result;
        if (techniqueArray.isArray()) {
            for (JsonNode techniqueNode : techniqueArray) {
                Technique technique = parseSingleTechnique(techniqueNode);
                if (technique != null) result.add(technique);
            }
        } else {
            Technique technique = parseSingleTechnique(techniqueArray);
            if (technique != null) result.add(technique);
        }
        return result;
    }
    
    private Technique parseSingleTechnique(JsonNode techniqueNode) {
        if (techniqueNode == null || techniqueNode.isNull()) return null;
        String name = techniqueNode.has("name") && techniqueNode.get("name") != null ? techniqueNode.get("name").asText() : null;
        String type = techniqueNode.has("type") && techniqueNode.get("type") != null ? techniqueNode.get("type").asText() : null;
        int damage = techniqueNode.has("damage") && techniqueNode.get("damage") != null ? techniqueNode.get("damage").asInt() : 0;
        String owner = techniqueNode.has("owner") && techniqueNode.get("owner") != null ? techniqueNode.get("owner").asText() : null;
        if (name == null) return null;
        return new Technique(name, type, damage, owner);
    }

    @Override
    public boolean canParse(File file) {
        return getType(file).equalsIgnoreCase("XML");    
    }
}
