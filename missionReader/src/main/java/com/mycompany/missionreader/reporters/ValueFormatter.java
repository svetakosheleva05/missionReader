/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.reporters;

import com.mycompany.missionreader.Mission;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author svetl
 */
public class ValueFormatter {

    public static String orNot(Object value) {
        return value == null ? "Не указано" : value.toString();
    }
    
    public static String costToString(int cost) {
        if (cost == 0) return "Не указано";
        return String.valueOf(cost);
    }
    
    public static List<String> listOrEmpty(List<?> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (Object item : items) {
            result.add(item.toString());
        }
        return result;
    }
    
    public static Map<String, String> basicInfo(Mission mission) {
        Map<String, String> basic = new LinkedHashMap<>();
        basic.put("ID миссии", orNot(mission.getMissionId()));
        basic.put("Дата", orNot(mission.getDate()));
        basic.put("Локация", orNot(mission.getLocation()));
        basic.put("Исход", orNot(mission.getOutcome()));
        return basic;
    }
    
}
