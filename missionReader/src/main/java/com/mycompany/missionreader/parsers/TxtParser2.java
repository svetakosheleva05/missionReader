/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.parsers;

import com.mycompany.missionreader.CivilianImpact;
import com.mycompany.missionreader.Curse;
import com.mycompany.missionreader.CurseRegister;
import com.mycompany.missionreader.EnemyActivity;
import com.mycompany.missionreader.EnvironmentConditions;
import com.mycompany.missionreader.Mission;
import com.mycompany.missionreader.MissionBuilder;
import com.mycompany.missionreader.MissionStorage;
import com.mycompany.missionreader.Sorcerer;
import com.mycompany.missionreader.SorcererRegister;
import com.mycompany.missionreader.Technique;
import com.mycompany.missionreader.enams.EnemyMobility;
import com.mycompany.missionreader.enams.EscalationRisk;
import com.mycompany.missionreader.enams.MissionOutcome;
import com.mycompany.missionreader.enams.PublicExposureRisk;
import com.mycompany.missionreader.enams.Visibility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author svetl
 */
public class TxtParser2 implements MissionParser {
                                                  
    private MissionBuilder builder;
    private SorcererRegister sorcererRegister;
    private CurseRegister curseRegister;
    private MissionStorage missionStorage;
    
    private List<String> evacuationZones = new ArrayList<>();
    private List<String> attackPatterns = new ArrayList<>();
    private List<String> statusEffects = new ArrayList<>();
    private List<String> notes = new ArrayList<>();
    
    public TxtParser2(SorcererRegister sorcererRegister, CurseRegister curseRegister, MissionStorage missionStorage, MissionBuilder builder) {
        this.sorcererRegister = sorcererRegister;
        this.curseRegister = curseRegister;
        this.missionStorage = missionStorage;
        this.builder = builder;
    }

    @Override
    public Mission parse(File file) {
        evacuationZones.clear();
        attackPatterns.clear();
        statusEffects.clear();
        notes.clear();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String currentSection = null;

            Map<String, String> missionData = new HashMap<>();
            Map<String, String> curseData = new HashMap<>();
            Map<String, String> environmentData = new HashMap<>();
            Map<String, String> civilianImpactData = new HashMap<>();
            Map<String, String> enemyActivityData = new HashMap<>();

            Map<String, String> currentSorcerer = null;
            Map<String, String> currentTechnique = null;
            List<Map<String, String>> sorcerersData = new ArrayList<>();
            List<Map<String, String>> techniquesData = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("[") && line.endsWith("]")) {
                    if (currentSorcerer != null && !currentSorcerer.isEmpty()) {
                        sorcerersData.add(currentSorcerer);
                        currentSorcerer = null;
                    }
                    if (currentTechnique != null && !currentTechnique.isEmpty()) {
                        techniquesData.add(currentTechnique);
                        currentTechnique = null;
                    }
                    currentSection = line.substring(1, line.length() - 1);
                    continue;
                }

                if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    if (currentSection == null) {
                        continue;
                    }

                    switch (currentSection) {
                        case "MISSION":
                            missionData.put(key, value);
                            break;
                        case "CURSE":
                            curseData.put(key, value);
                            break;
                        case "SORCERER":
                            if (currentSorcerer == null) {
                                currentSorcerer = new HashMap<>();
                            }
                            currentSorcerer.put(key, value);
                            break;
                        case "TECHNIQUE":
                            if (currentTechnique == null) {
                                currentTechnique = new HashMap<>();
                            }
                            currentTechnique.put(key, value);
                            break;
                        case "ENVIRONMENT":
                            environmentData.put(key, value);
                            break;
                        case "CIVILIAN_IMPACT":
                            civilianImpactData.put(key, value);
                            break;
                        case "EVACUATION_ZONE":
                            evacuationZones.add(value);
                            break;
                        case "ENEMY_ACTIVITY":
                            enemyActivityData.put(key, value);
                            break;
                        case "ATTACK_PATTERN":
                            attackPatterns.add(value);
                            break;
                        case "STATUS_EFFECT":
                            statusEffects.add(value);
                            break;
                        case "NOTES":
                            notes.add(value);
                            break;
                    }
                }
            }

            if (currentSorcerer != null && !currentSorcerer.isEmpty()) {
                sorcerersData.add(currentSorcerer);
            }
            if (currentTechnique != null && !currentTechnique.isEmpty()) {
                techniquesData.add(currentTechnique);
            }

            if (missionData.containsKey("missionId")) {
                builder.setMissionId(missionData.get("missionId"));
            }
            if (missionData.containsKey("date")) {
                builder.setDate(LocalDate.parse(missionData.get("date")));
            }
            if (missionData.containsKey("location")) {
                builder.setLocation(missionData.get("location"));
            }
            if (missionData.containsKey("outcome")) {
                builder.setOutcome(MissionOutcome.fromStringToEnum(missionData.get("outcome")));
            }
            if (missionData.containsKey("damageCost")) {
                builder.setDamageCost(Integer.parseInt(missionData.get("damageCost")));
            }

            if (!curseData.isEmpty()) {
                Curse curse = curseRegister.getOrCreate(new Curse(
                    curseData.get("name"),
                    curseData.get("threatLevel")
                ));
                builder.setTargetCurse(curse);
            }

            List<Sorcerer> sorcerers = new ArrayList<>();
            for (Map<String, String> sorcererData : sorcerersData) {
                if (sorcererData.containsKey("name") && sorcererData.containsKey("rank")) {
                    Sorcerer sorcerer = sorcererRegister.getOrCreate(new Sorcerer(
                        sorcererData.get("name"),
                        sorcererData.get("rank")
                    ));
                    sorcerers.add(sorcerer);
                }
            }
            builder.setParticipants(sorcerers);

            List<Technique> techniques = new ArrayList<>();
            for (Map<String, String> techniqueData : techniquesData) {
                if (techniqueData.containsKey("name") && 
                    techniqueData.containsKey("type") && 
                    techniqueData.containsKey("damage") && 
                    techniqueData.containsKey("owner")) {
                    
                    Technique technique = new Technique(
                        techniqueData.get("name"),
                        techniqueData.get("type"),
                        Integer.parseInt(techniqueData.get("damage")),
                        techniqueData.get("owner")
                    );
                    techniques.add(technique);
                }
            }
            if (!techniques.isEmpty()) {
                builder.setTechniqueUsed(techniques);
            }

            if (!civilianImpactData.isEmpty()) {
                CivilianImpact civilianImpact = new CivilianImpact();
                
                if (civilianImpactData.containsKey("evacuated")) {
                    civilianImpact.setEvacuated(Integer.parseInt(civilianImpactData.get("evacuated")));
                }
                if (civilianImpactData.containsKey("injured")) {
                    civilianImpact.setInjured(Integer.parseInt(civilianImpactData.get("injured")));
                }
                if (civilianImpactData.containsKey("missing")) {
                    civilianImpact.setMissing(Integer.parseInt(civilianImpactData.get("missing")));
                }
                if (civilianImpactData.containsKey("publicExposureRisk")) {
                    civilianImpact.setPublicExposureRisk(
                        PublicExposureRisk.fromStringToEnum(civilianImpactData.get("publicExposureRisk"))
                    );
                }
                builder.setCivilianImpact(civilianImpact);
            }

            if (!enemyActivityData.isEmpty() || !attackPatterns.isEmpty()) {
                EnemyActivity enemyActivity = new EnemyActivity();
                
                if (enemyActivityData.containsKey("behaviorType")) {
                    enemyActivity.setBehaviorType(enemyActivityData.get("behaviorType"));
                }
                if (enemyActivityData.containsKey("mobility")) {
                    enemyActivity.setMobility(EnemyMobility.fromStringToEnum(enemyActivityData.get("mobility")));
                }
                if (enemyActivityData.containsKey("escalationRisk")) {
                    enemyActivity.setEscalationRisk(EscalationRisk.fromStringToEnum(enemyActivityData.get("escalationRisk")));
                }
                if (!attackPatterns.isEmpty()) {
                    enemyActivity.setAttackPatterns(new ArrayList<>(attackPatterns));
                }
                
                builder.setEnemyActivity(enemyActivity);
            }

            if (!environmentData.isEmpty()) {
                EnvironmentConditions conditions = new EnvironmentConditions();

                if (environmentData.containsKey("weather")) {
                    conditions.setWeather(environmentData.get("weather"));
                }
                if (environmentData.containsKey("timeOfDay")) {
                    conditions.setTimeOfDay(environmentData.get("timeOfDay"));
                }
                if (environmentData.containsKey("visibility")) {
                    conditions.setVisibility(Visibility.fromStringToEnum(environmentData.get("visibility")));
                }
                if (environmentData.containsKey("cursedEnergyDensity")) {
                    conditions.setCursedEnergyDensity(Integer.parseInt(environmentData.get("cursedEnergyDensity")));
                }

                builder.setEnvironmentConditions(conditions);
            }

            java.util.HashMap<String, java.util.ArrayList<String>> otherData = new java.util.HashMap<>();
            
            if (!evacuationZones.isEmpty()) {
                otherData.put("evacuationZones", new java.util.ArrayList<>(evacuationZones));
            }
            if (!statusEffects.isEmpty()) {
                otherData.put("statusEffects", new java.util.ArrayList<>(statusEffects));
            }
            if (!notes.isEmpty()) {
                otherData.put("notes", new java.util.ArrayList<>(notes));
            }
            
            if (!otherData.isEmpty()) {
                builder.setOtherData(otherData);
            }

            return builder.build();

        } catch (IOException e) {
            System.err.println("Ошибка при парсинге текстового файла: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean canParse(File file) {
        if (!file.getName().toLowerCase().endsWith(".txt")) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String firstLine = reader.readLine();
            if (firstLine == null) {
                return false;
            }
            return firstLine.trim().startsWith("[MISSION]");
        } catch (IOException e) {
            return false;
        }
    }
}