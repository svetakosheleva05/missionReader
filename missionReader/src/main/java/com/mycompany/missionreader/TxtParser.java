/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author svetl
 */
public class TxtParser implements MissionParser {  
    private SorcererRegister sorcererRegister;
    private CurseRegister curseRegister;
    private MissionStorage missionStorage;
    
    public TxtParser(SorcererRegister sorcererRegister, CurseRegister curseRegister, MissionStorage missionStorage) {
        this.sorcererRegister = sorcererRegister;
        this.curseRegister = curseRegister;
        this.missionStorage = missionStorage;
    }
    
    @Override
    public Mission parse(File file) {
        try {

            Map<String, String> data = readFileToMap(file);
            String missionId = data.get("missionId");
            LocalDate date = LocalDate.parse(data.get("date"));
            String location = data.get("location");
            String outcome = data.get("outcome");
            int damageCost = Integer.parseInt(data.get("damageCost"));
            
            Curse curse = curseRegister.getOrCreate(new Curse(
                data.get("curse.name"),
                data.get("curse.threatLevel")
            ));
            
            List<Sorcerer> sorcerers = parseSorcerers(data);
            List<Technique> techniques = parseTechniques(data, sorcerers);
            String note = data.get("note");
            Mission mission = new Mission(missionId, date, location, curse, outcome, damageCost, sorcerers, techniques, note);;
            return mission;
        } catch (Exception e) {
            System.err.println("Ошибка при парсинге TXT: " + e.getMessage());
            return null;
        }
    }

    private Map<String, String> readFileToMap(File file) throws IOException {
        Map<String, String> data = new HashMap<>();
        List<String> lines = Files.readAllLines(file.toPath());
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            
            int colonPos = line.indexOf(':');
            if (colonPos > 0) {
                String key = line.substring(0, colonPos).trim();
                String value = line.substring(colonPos + 1).trim();
                data.put(key, value);
            }
        }   
        return data;
    }

    private List<Sorcerer> parseSorcerers(Map<String, String> data) {
        List<Sorcerer> result = new ArrayList<>();
        
        int index = 0;
        while (true) {
            String nameKey = "sorcerer[" + index + "].name";
            String rankKey = "sorcerer[" + index + "].rank";
            
            String name = data.get(nameKey);
            String rank = data.get(rankKey);
            
            if (name == null || rank == null) {
                break;
            }
            
            Sorcerer sorcerer = sorcererRegister.getOrCreate(new Sorcerer(name, rank));
            result.add(sorcerer);
            index++;
        }
        return result;
    }
    
    private List<Technique> parseTechniques(Map<String, String> data, List<Sorcerer> sorcerers) {
        List<Technique> result = new ArrayList<>();
        
        int index = 0;
        while (true) {
            String nameKey = "technique[" + index + "].name";
            String typeKey = "technique[" + index + "].type";
            String ownerKey = "technique[" + index + "].owner";
            String damageKey = "technique[" + index + "].damage";
            
            String name = data.get(nameKey);
            String type = data.get(typeKey);
            String ownerName = data.get(ownerKey);
            String damageStr = data.get(damageKey);
            
            if (name == null || type == null || ownerName == null || damageStr == null) {
                break;
            }
            
            int damage = Integer.parseInt(damageStr);
            Sorcerer owner = sorcererRegister.findSorcererByName(ownerName);
            result.add(new Technique(name, type, damage, owner));
            index++;
        }
        
        return result;
    }
}
