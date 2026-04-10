/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mycompany.missionreader.Curse;
import com.mycompany.missionreader.CurseRegister;
import com.mycompany.missionreader.EnemyActivity;
import com.mycompany.missionreader.Mission;
import com.mycompany.missionreader.MissionBuilder;
import com.mycompany.missionreader.MissionStorage;
import com.mycompany.missionreader.Sorcerer;
import com.mycompany.missionreader.SorcererRegister;
import com.mycompany.missionreader.Technique;
import com.mycompany.missionreader.enams.EnemyMobility;
import com.mycompany.missionreader.enams.EscalationRisk;
import com.mycompany.missionreader.enams.MissionOutcome;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

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
                String curseName = curseNode.has("name") && curseNode.get("name") != null 
                    ? curseNode.get("name").asText() : null;
                String threatLevel = curseNode.has("threatLevel") && curseNode.get("threatLevel") != null 
                    ? curseNode.get("threatLevel").asText() : null;
                if (curseName != null && threatLevel != null) {
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
            
            if (jsonNode.has("enemyActivity") && jsonNode.get("enemyActivity") != null && !jsonNode.get("enemyActivity").isNull()) {
                JsonNode enemyActivityNode = jsonNode.get("enemyActivity");
                EnemyActivity enemyActivity = new EnemyActivity();
                
                if (enemyActivityNode.has("behaviorType") && !enemyActivityNode.get("behaviorType").isNull()) {
                    enemyActivity.setBehaviorType(enemyActivityNode.get("behaviorType").asText());
                }
                
                if (enemyActivityNode.has("attackPatterns") && !enemyActivityNode.get("attackPatterns").isNull()) {
                    ArrayList<String> attackPatterns = parseAttackPatterns(enemyActivityNode.get("attackPatterns"));
                    if (!attackPatterns.isEmpty()) {
                        enemyActivity.setAttackPatterns(attackPatterns);
                    }
                }
                
                if (enemyActivityNode.has("countermeasuresUsed") && !enemyActivityNode.get("countermeasuresUsed").isNull()) {
                    ArrayList<String> countermeasuresUsed = parseCountermeasures(enemyActivityNode.get("countermeasuresUsed"));
                    if (!countermeasuresUsed.isEmpty()) {
                        enemyActivity.setCountermeasuresUsed(countermeasuresUsed);
                    }
                }
                
                if (enemyActivityNode.has("targetPriority") && !enemyActivityNode.get("targetPriority").isNull()) {
                    ArrayList<String> targetPriority = parseTargetPriority(enemyActivityNode.get("targetPriority"));
                    if (!targetPriority.isEmpty()) {
                        enemyActivity.setTargetPriority(targetPriority);
                    }
                }
                
                if (enemyActivityNode.has("mobility") && !enemyActivityNode.get("mobility").isNull()) {
                    enemyActivity.setMobility(EnemyMobility.fromStringToEnum(enemyActivityNode.get("mobility").asText()));
                }
                
                if (enemyActivityNode.has("escalationRisk") && !enemyActivityNode.get("escalationRisk").isNull()) {
                    enemyActivity.setEscalationRisk(EscalationRisk.fromStringToEnum(enemyActivityNode.get("escalationRisk").asText()));
                }
                
                builder.setEnemyActivity(enemyActivity);
            }
            
            return builder.build();
            
        } catch (IOException e) {
            System.err.println("Ошибка при парсинге XML: " + e.getMessage());
            return null;
        }
    }
    
    private ArrayList<String> parseTargetPriority(JsonNode targetPriorityNode) {
        ArrayList<String> result = new ArrayList<>();
        
        if (targetPriorityNode == null || targetPriorityNode.isNull()) {
            return result;
        }
        
        JsonNode priorityNode = targetPriorityNode.get("targetPriority");
        if (priorityNode == null || priorityNode.isNull()) {
            return result;
        }
        
        if (priorityNode.isArray()) {
            for (JsonNode priority : priorityNode) {
                if (priority != null && !priority.isNull()) {
                    result.add(priority.asText());
                }
            }
        } else {
            result.add(priorityNode.asText());
        }
        
        return result;
    }
    
    private ArrayList<String> parseCountermeasures(JsonNode countermeasuresNode) {
        ArrayList<String> result = new ArrayList<>();
        
        if (countermeasuresNode == null || countermeasuresNode.isNull()) {
            return result;
        }
        
        JsonNode measureArray = countermeasuresNode.get("measure");
        if (measureArray == null || measureArray.isNull()) {
            return result;
        }
        
        if (measureArray.isArray()) {
            for (JsonNode measure : measureArray) {
                if (measure != null && !measure.isNull()) {
                    result.add(measure.asText());
                }
            }
        } else {
            result.add(measureArray.asText());
        }
        
        return result;
    }
    
    private ArrayList<String> parseAttackPatterns(JsonNode attackPatternsNode) {
        ArrayList<String> result = new ArrayList<>();
        
        if (attackPatternsNode == null || attackPatternsNode.isNull()) {
            return result;
        }
        
        JsonNode patternArray = attackPatternsNode.get("pattern");
        if (patternArray == null || patternArray.isNull()) {
            return result;
        }
        
        if (patternArray.isArray()) {
            for (JsonNode pattern : patternArray) {
                if (pattern != null && !pattern.isNull()) {
                    result.add(pattern.asText());
                }
            }
        } else {
            result.add(patternArray.asText());
        }
        
        return result;
    }
    
    private ArrayList<Sorcerer> parseSorcerers(JsonNode sorcerersNode) {
        ArrayList<Sorcerer> result = new ArrayList<>();
        
        if (sorcerersNode == null || sorcerersNode.isNull()) {
            return result;
        }
        
        if (!sorcerersNode.has("sorcerer")) {
            return result;
        }
        
        JsonNode sorcererArray = sorcerersNode.get("sorcerer");
        if (sorcererArray == null || sorcererArray.isNull()) {
            return result;
        }
        
        if (sorcererArray.isArray()) {
            for (JsonNode sorcererNode : sorcererArray) {
                Sorcerer sorcerer = parseSingleSorcerer(sorcererNode);
                if (sorcerer != null) {
                    result.add(sorcerer);
                }
            }
        } else {
            Sorcerer sorcerer = parseSingleSorcerer(sorcererArray);
            if (sorcerer != null) {
                result.add(sorcerer);
            }
        }
        
        return result;
    }
    
    private Sorcerer parseSingleSorcerer(JsonNode sorcererNode) {
        if (sorcererNode == null || sorcererNode.isNull()) {
            return null;
        }
        
        String name = sorcererNode.has("name") && sorcererNode.get("name") != null 
            ? sorcererNode.get("name").asText() : null;
        String rank = sorcererNode.has("rank") && sorcererNode.get("rank") != null 
            ? sorcererNode.get("rank").asText() : null;
        
        if (name == null) {
            return null;
        }
        
        return sorcererRegister.getOrCreate(new Sorcerer(name, rank));
    }
    
    private ArrayList<Technique> parseTechniques(JsonNode techniquesNode) {
        ArrayList<Technique> result = new ArrayList<>();
        
        if (techniquesNode == null || techniquesNode.isNull()) {
            return result;
        }
        
        if (!techniquesNode.has("technique")) {
            return result;
        }
        
        JsonNode techniqueArray = techniquesNode.get("technique");
        if (techniqueArray == null || techniqueArray.isNull()) {
            return result;
        }
        
        if (techniqueArray.isArray()) {
            for (JsonNode techniqueNode : techniqueArray) {
                Technique technique = parseSingleTechnique(techniqueNode);
                if (technique != null) {
                    result.add(technique);
                }
            }
        } else {
            Technique technique = parseSingleTechnique(techniqueArray);
            if (technique != null) {
                result.add(technique);
            }
        }
        
        return result;
    }
    
    private Technique parseSingleTechnique(JsonNode techniqueNode) {
        if (techniqueNode == null || techniqueNode.isNull()) {
            return null;
        }
        
        String name = techniqueNode.has("name") && techniqueNode.get("name") != null 
            ? techniqueNode.get("name").asText() : null;
        String type = techniqueNode.has("type") && techniqueNode.get("type") != null 
            ? techniqueNode.get("type").asText() : null;
        int damage = techniqueNode.has("damage") && techniqueNode.get("damage") != null 
            ? techniqueNode.get("damage").asInt() : 0;
        String owner = techniqueNode.has("owner") && techniqueNode.get("owner") != null 
            ? techniqueNode.get("owner").asText() : null;
        
        if (name == null) {
            return null;
        }
        
        return new Technique(name, type, damage, owner);
    }

    @Override
    public boolean canParse(File file) {
        return getType(file).equalsIgnoreCase("XML");    
    }
}
