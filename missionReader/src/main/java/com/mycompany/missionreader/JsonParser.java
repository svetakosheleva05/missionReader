/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author svetl
 */
public class JsonParser implements MissionParser {
    
    private SorcererRegister sorcererRegister;
    private CurseRegister curseRegister;
    
    public JsonParser(SorcererRegister sorcererRegister, CurseRegister curseRegister) {
        this.sorcererRegister = sorcererRegister;
        this.curseRegister = curseRegister;
    }
    
    @Override
    public boolean canParse(File file) {
        String name = file.getName();
        String extension = name.substring(name.lastIndexOf(".") + 1);
        return extension.equalsIgnoreCase("json");
    }
    
    @Override
    public Mission parse(File file) {
        
        if (!canParse(file)) {
            System.out.println("Расширение файла неверное");
            return null;
        }
        
        try {
        ObjectMapper objectMapper = new ObjectMapper();
        
        JsonNode jsonNode = objectMapper.readTree(file);
        
        String missionId = jsonNode.get("missionId").asText();
        String rawDate = jsonNode.get("date").asText();
        LocalDate date = LocalDate.parse(rawDate); 
        String location = jsonNode.get("location").asText(); 
        String outcome = jsonNode.get("outcome").asText();
        int damageCost = jsonNode.get("damageCost").asInt();
        
        JsonNode curseNode = jsonNode.get("curse");
        
        Curse curse = curseRegister.getOrCreate(new Curse(
                curseNode.get("name").asText(),
                curseNode.get("threatLevel").asText()));
        
        JsonNode sorcerersNode = jsonNode.get("sorcerers");
        List<Sorcerer> sorcerers = new ArrayList<>();
        
        for (JsonNode sorcererNode : sorcerersNode) {
            Sorcerer sorcererTemp = new Sorcerer(
                sorcererNode.get("name").asText(),
                sorcererNode.get("rank").asText()
            );
            sorcerers.add(sorcererRegister.getOrCreate(sorcererTemp));
        }
        
        JsonNode techniquesNode = jsonNode.get("techniques");
        List<Technique> techniques = new ArrayList<>();
        
        for (JsonNode techniqueNode : techniquesNode) {            
            Technique technique = new Technique(
                techniqueNode.get("name").asText(),
                techniqueNode.get("type").asText(),
                techniqueNode.get("damage").asInt(),
                sorcererRegister.findSorcererByName(techniqueNode.get("owner").asText())
            );
            techniques.add(technique);  
        } 
        
        String comment;
         if (jsonNode.has("comment")) {
            comment = jsonNode.get("comment").asText();
        } else {
            comment = null;
        }

        return new Mission(missionId, date, location, curse, outcome, damageCost, sorcerers, techniques, comment);
        } catch (IOException e) {
            System.out.println("Невалидный файл"); 
            return null;
        }
    }
}
