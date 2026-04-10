/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.parsers;

import com.mycompany.missionreader.*;
import com.mycompany.missionreader.enams.MissionOutcome;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author svetl
 */

public class BinaryParser implements MissionParser {

    private MissionBuilder builder;
    private SorcererRegister sorcererRegister;
    private CurseRegister curseRegister;
    private MissionStorage missionStorage;
    
    private List<Sorcerer> sorcerers = new ArrayList<>();
    private List<Technique> techniques = new ArrayList<>();
    private List<OperationTimeline> timelineEvents = new ArrayList<>();
    private List<String> tempAttackPatterns = new ArrayList<>();
    private Map<String, Integer> civilianImpact = new HashMap<>();
    
    public BinaryParser(SorcererRegister sorcererRegister, 
                         CurseRegister curseRegister, 
                         MissionStorage missionStorage, 
                         MissionBuilder builder) {
        this.sorcererRegister = sorcererRegister;
        this.curseRegister = curseRegister;
        this.missionStorage = missionStorage;
        this.builder = builder;
    }
    
    @Override
    public Mission parse(File file) {
        clearTempStorages();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\|");
                if (parts.length == 0) continue;
                
                String lineType = parts[0];
                
                switch (lineType) {
                    case "MISSION_CREATED":
                        parseMissionCreated(parts);
                        break;
                    case "CURSE_DETECTED":
                        parseCurseDetected(parts);
                        break;
                    case "SORCERER_ASSIGNED":
                        parseSorcererAssigned(parts);
                        break;
                    case "TECHNIQUE_USED":
                        parseTechniqueUsed(parts);
                        break;
                    case "TIMELINE_EVENT":
                        parseTimelineEvent(parts);
                        break;
                    case "ENEMY_ACTION":
                        parseEnemyAction(parts);
                        break;
                    case "CIVILIAN_IMPACT":
                        parseCivilianImpact(parts);
                        break;
                    case "MISSION_RESULT":
                        parseMissionResult(parts);
                        break;
                }
            }
            
            addDataToBuilder();
            return builder.build();
            
        } catch (IOException e) {
            System.err.println("Ошибка при парсинге странного файла: " + e.getMessage());
            return null;
        }
    }
    
    private void clearTempStorages() {
        sorcerers.clear();
        techniques.clear();
        timelineEvents.clear();
        tempAttackPatterns.clear();
        civilianImpact.clear();
    }
    
    private void parseMissionCreated(String[] parts) {
        if (parts.length >= 4) {
            builder.setMissionId(parts[1]);
            builder.setDate(LocalDate.parse(parts[2]));
            builder.setLocation(parts[3]);
        }
    }
    
    private void parseCurseDetected(String[] parts) {
        if (parts.length >= 3) {
            Curse curse = curseRegister.getOrCreate(new Curse(parts[1], parts[2]));
            builder.setTargetCurse(curse);
        }
    }
    
    private void parseSorcererAssigned(String[] parts) {
        if (parts.length >= 3) {
            Sorcerer sorcerer = sorcererRegister.getOrCreate(new Sorcerer(parts[1], parts[2]));
            sorcerers.add(sorcerer);
        }
    }
    
    private void parseTechniqueUsed(String[] parts) {
        if (parts.length >= 5) {
            Technique technique = new Technique(
                parts[1],
                parts[2],
                Integer.parseInt(parts[4]),
                parts[3]
            );
            techniques.add(technique);
        }
    }
    
    private void parseTimelineEvent(String[] parts) {
        if (parts.length >= 4) {
            OperationTimeline operationTimeline = new OperationTimeline();
            operationTimeline.setTimestamp(LocalDateTime.parse(parts[1]));
            operationTimeline.setType(parts[2]);
            operationTimeline.setDescription(parts[3]);
            timelineEvents.add(operationTimeline);
        }
    }
    
    private void parseEnemyAction(String[] parts) {

        if (parts.length >= 3) {
            String actionType = parts[1];
            String actionDesc = parts[2];
            tempAttackPatterns.add(actionType + ": " + actionDesc);
        } else if (parts.length >= 2) {
            tempAttackPatterns.add(parts[1]);
        }
    }
    
    private void parseCivilianImpact(String[] parts) {
        for (int i = 1; i < parts.length; i++) {
            String[] civilianImpactParts = parts[i].split("=");
            if (civilianImpactParts.length == 2) {
                civilianImpact.put(civilianImpactParts[0], Integer.valueOf(civilianImpactParts[1]));
            }
        }
    }
    
    private void parseMissionResult(String[] parts) {
        if (parts.length >= 2) {
            builder.setOutcome(MissionOutcome.fromStringToEnum(parts[1]));
        }
        if (parts.length >= 3) {
            String[] damageCostPair = parts[2].split("=");
            if (damageCostPair.length == 2 && damageCostPair[0].equals("damageCost")) {
                builder.setDamageCost(Integer.parseInt(damageCostPair[1]));
            }
        }
    }
    
    private void addDataToBuilder(){
            builder.setParticipants(sorcerers);
       
            builder.setTechniqueUsed(techniques);
        
        if (!tempAttackPatterns.isEmpty()) {
            EnemyActivity enemyActivity = new EnemyActivity();
            enemyActivity.setAttackPatterns(new ArrayList<>(tempAttackPatterns));
            builder.setEnemyActivity(enemyActivity);
        }
        
        if (!civilianImpact.isEmpty()) {
            CivilianImpact impact = new CivilianImpact();
            if (civilianImpact.containsKey("evacuated")) {
                impact.setEvacuated(civilianImpact.get("evacuated"));
            }
            if (civilianImpact.containsKey("injured")) {
                impact.setInjured(civilianImpact.get("injured"));
            }
            if (civilianImpact.containsKey("missing")) {
                impact.setMissing(civilianImpact.get("missing"));
            }
            builder.setCivilianImpact(impact);
        }
        
        builder.setOperationTimelines(timelineEvents);
        
    }
    
    @Override
    public boolean canParse(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String firstLine = reader.readLine();
            if (firstLine == null) {
                return false;
            }
            return firstLine.startsWith("MISSION_CREATED|");
        } catch (IOException e) {
            return false;
        }
    }
}