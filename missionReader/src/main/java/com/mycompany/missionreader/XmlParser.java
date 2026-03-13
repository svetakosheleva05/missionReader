/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
    
    
    public XmlParser(SorcererRegister sorcererRegister, CurseRegister curseRegister, MissionStorage missionStorage) {
        this.sorcererRegister = sorcererRegister;
        this.curseRegister = curseRegister;
        this.missionStorage = missionStorage;
    }
    
    @Override
    public Mission parse(File file) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode root = xmlMapper.readTree(file);
            
            String missionId = root.get("missionId").asText();
            String rawDate = root.get("date").asText();
            LocalDate date = LocalDate.parse(rawDate);
            String location = root.get("location").asText();
            String outcome = root.get("outcome").asText();
            int damageCost = root.get("damageCost").asInt();
            
            JsonNode curseNode = root.get("curse");
            String curseName = curseNode.get("name").asText();
            String threatLevel = curseNode.get("threatLevel").asText();
            Curse curse = curseRegister.getOrCreate(new Curse(curseName, threatLevel));
            
            List<Sorcerer> sorcerers = parseSorcerers(root.get("sorcerers"));
            List<Technique> techniques = parseTechniques(root.get("techniques"), sorcerers);
            String comment = root.has("comment") ? root.get("comment").asText() : null;
            
            return new Mission(missionId, date, location, curse, outcome, damageCost, sorcerers, techniques, comment);
            
        } catch (IOException e) {
            System.err.println("Ошибка при парсинге XML: " + e.getMessage());
            return null;
        }
    }

    private List<Sorcerer> parseSorcerers(JsonNode sorcerersNode) {
        List<Sorcerer> result = new ArrayList<>();

        JsonNode sorcererArray = sorcerersNode.get("sorcerer");
        
        if (sorcererArray.isArray()) {
            for (JsonNode sorcererNode : sorcererArray) {
                Sorcerer sorcerer = parseSingleSorcerer(sorcererNode);
                result.add(sorcerer);
            }
        } else {
            Sorcerer sorcerer = parseSingleSorcerer(sorcererArray);
            result.add(sorcerer);
        }
        
        return result;
    }
    
    private Sorcerer parseSingleSorcerer(JsonNode sorcererNode) {
        String name = sorcererNode.get("name").asText();
        String rank = sorcererNode.get("rank").asText();
        return sorcererRegister.getOrCreate(new Sorcerer(name, rank));
    }
    
    private List<Technique> parseTechniques(JsonNode techniquesNode, List<Sorcerer> sorcerers) {
        List<Technique> result = new ArrayList<>();

        JsonNode techniqueArray = techniquesNode.get("technique");
        
        if (techniqueArray.isArray()) {
            for (JsonNode techniqueNode : techniqueArray) {
                Technique technique = parseSingleTechnique(techniqueNode);
                result.add(technique);
            }
        } else {
            Technique technique = parseSingleTechnique(techniqueArray);
            result.add(technique);
        }   
        return result;
    }

    private Technique parseSingleTechnique(JsonNode techniqueNode) {
        
        String name = techniqueNode.get("name").asText();
        String type = techniqueNode.get("type").asText();
        int damage = techniqueNode.get("damage").asInt();
        Sorcerer owner = sorcererRegister.findSorcererByName(techniqueNode.get("owner").asText());
        
        return new Technique(name, type, damage, owner);
    }
}
