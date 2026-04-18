/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.parsers;

import com.mycompany.missionreader.enams.MissionOutcome;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class JsonParser implements MissionParser {
      
    private MissionBuilder builder;
    private SorcererRegister sorcererRegister;
    private CurseRegister curseRegister;       
    private MissionStorage missionStorage;
    
    public JsonParser(SorcererRegister sorcererRegister, CurseRegister curseRegister, MissionStorage missionStorage, MissionBuilder builder) {
        this.sorcererRegister = sorcererRegister;
        this.curseRegister = curseRegister;
        this.missionStorage = missionStorage;
        this.builder = builder;
    }
    
    @Override
    public Mission parse(File file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(file);
            
            if (jsonNode.has("missionId")) {
                builder.setMissionId(jsonNode.get("missionId").asText());
            }

            if (jsonNode.has("date")) {
                builder.setDate(LocalDate.parse(jsonNode.get("date").asText()));
            }

            if (jsonNode.has("location")) {
                builder.setLocation(jsonNode.get("location").asText());
            }

            if (jsonNode.has("outcome")) {
                builder.setOutcome(MissionOutcome.fromStringToEnum(jsonNode.get("outcome").asText()));
            }

            if (jsonNode.has("damageCost")) {
                builder.setDamageCost(jsonNode.get("damageCost").asInt());
            }

            if (jsonNode.has("curse") && jsonNode.get("curse") != null) {
                JsonNode curseNode = jsonNode.get("curse");
                String curseName = curseNode.has("name") ? curseNode.get("name").asText() : null;
                String threatLevel = curseNode.has("threatLevel") ? curseNode.get("threatLevel").asText() : null;
                if (curseName != null && threatLevel != null) {
                    builder.setTargetCurse(curseRegister.getOrCreate(new Curse(curseName, threatLevel)));
                }
            }

            if (jsonNode.has("sorcerers")) {
                List<Sorcerer> sorcerers = new ArrayList<>();
                for (JsonNode sorcererNode : jsonNode.get("sorcerers")) {
                    String name = sorcererNode.has("name") ? sorcererNode.get("name").asText() : null;
                    String rank = sorcererNode.has("rank") ? sorcererNode.get("rank").asText() : null;
                    if (name != null) {
                        sorcerers.add(sorcererRegister.getOrCreate(new Sorcerer(name, rank)));
                    }
                }
                if (!sorcerers.isEmpty()) {
                    builder.setParticipants(sorcerers);
                }
            }

            if (jsonNode.has("techniques")) {
                List<Technique> techniques = new ArrayList<>();
                for (JsonNode techniqueNode : jsonNode.get("techniques")) {
                    String name = techniqueNode.has("name") ? techniqueNode.get("name").asText() : null;
                    String type = techniqueNode.has("type") ? techniqueNode.get("type").asText() : null;
                    int damage = techniqueNode.has("damage") ? techniqueNode.get("damage").asInt() : 0;
                    String owner = techniqueNode.has("owner") ? techniqueNode.get("owner").asText() : null;
                    if (name != null) {
                        techniques.add(new Technique(name, type, damage, owner));
                    }
                }
                if (!techniques.isEmpty()) {
                    builder.setTechniqueUsed(techniques);
                }
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
                
                if (enemyNode.has("targetPriority") && enemyNode.get("targetPriority").isArray()) {
                    ArrayList<String> targets = new ArrayList<>();
                    for (JsonNode t : enemyNode.get("targetPriority")) targets.add(t.asText());
                    activity.setTargetPriority(targets);
                }
                if (enemyNode.has("attackPatterns") && enemyNode.get("attackPatterns").isArray()) {
                    ArrayList<String> patterns = new ArrayList<>();
                    for (JsonNode p : enemyNode.get("attackPatterns")) patterns.add(p.asText());
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

            if (jsonNode.has("operationTimeline") && jsonNode.get("operationTimeline").isArray()) {
                List<OperationTimeline> timelineList = new ArrayList<>();
                for (JsonNode timelineNode : jsonNode.get("operationTimeline")) {
                    OperationTimeline timeline = new OperationTimeline();
                    if (timelineNode.has("timestamp")) timeline.setTimestamp(LocalDateTime.parse(timelineNode.get("timestamp").asText()));
                    if (timelineNode.has("type")) timeline.setType(timelineNode.get("type").asText());
                    if (timelineNode.has("description")) timeline.setDescription(timelineNode.get("description").asText());
                    timelineList.add(timeline);
                }
                if (!timelineList.isEmpty()) builder.setOperationTimelines(timelineList);
            }

            if (jsonNode.has("operationTags") && jsonNode.get("operationTags").isArray()) {
                java.util.HashMap<String, ArrayList<String>> otherData = new java.util.HashMap<>();
                ArrayList<String> tags = new ArrayList<>();
                for (JsonNode tag : jsonNode.get("operationTags")) tags.add(tag.asText());
                otherData.put("operationTags", tags);
                builder.setOtherData(otherData);
            }

            return builder.build();

        } catch (IOException e) {
            System.out.println("Ошибка при парсинге JSON: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean canParse(File file) {
        return getType(file).equalsIgnoreCase("JSON");
    }
}
