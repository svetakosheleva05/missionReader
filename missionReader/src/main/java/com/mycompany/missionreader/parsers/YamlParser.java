/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.mycompany.missionreader.Curse;
import com.mycompany.missionreader.CurseRegister;
import com.mycompany.missionreader.EconomicAssessment;
import com.mycompany.missionreader.Mission;
import com.mycompany.missionreader.MissionBuilder;
import com.mycompany.missionreader.MissionStorage;
import com.mycompany.missionreader.Sorcerer;
import com.mycompany.missionreader.SorcererRegister;
import com.mycompany.missionreader.Technique;
import com.mycompany.missionreader.enams.MissionOutcome;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author svetl
 */
public class YamlParser implements MissionParser {

    private MissionBuilder builder;
    private SorcererRegister sorcererRegister;
    private CurseRegister curseRegister;       
    private MissionStorage missionStorage;
    
    public YamlParser(SorcererRegister sorcererRegister, CurseRegister curseRegister, MissionStorage missionStorage, MissionBuilder builder) {
        this.sorcererRegister = sorcererRegister;
        this.curseRegister = curseRegister;
        this.missionStorage = missionStorage;
        this.builder = builder;
    }
    
    @Override
    public Mission parse(File file) {
        try {
            YAMLMapper yamlMapper = new YAMLMapper(); 
            JsonNode jsonNode = yamlMapper.readTree(file);

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
            
            if (jsonNode.has("techniques") && jsonNode.get("techniques") != null) {
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
                JsonNode economicNode = jsonNode.get("economicAssessment");
                int totalDamageCost = economicNode.has("totalDamageCost") ? economicNode.get("totalDamageCost").asInt() : 0;
                int infrastructureDamage = economicNode.has("infrastructureDamage") ? economicNode.get("infrastructureDamage").asInt() : 0;
                int transportDamage = economicNode.has("transportDamage") ? economicNode.get("transportDamage").asInt() : 0;
                int commercialDamage = economicNode.has("commercialDamage") ? economicNode.get("commercialDamage").asInt() : 0;
                int recoveryEstimateDays = economicNode.has("recoveryEstimateDays") ? economicNode.get("recoveryEstimateDays").asInt() : 0;
                boolean insuranceCovered = economicNode.has("insuranceCovered") && economicNode.get("insuranceCovered").asBoolean();

                EconomicAssessment economicAssessment = new EconomicAssessment(
                    totalDamageCost, infrastructureDamage, commercialDamage, 
                    transportDamage, recoveryEstimateDays, insuranceCovered
                );
                builder.setEconomicAssessment(economicAssessment);
            }

            return builder.build();

        } catch (IOException e) {
            System.out.println("Ошибка при парсинге YAML файла: " + e.getMessage());
            return null;
        }
    }


    @Override
    public boolean canParse(File file) {
        return getType(file).equalsIgnoreCase("YAML");
    }
    
}
