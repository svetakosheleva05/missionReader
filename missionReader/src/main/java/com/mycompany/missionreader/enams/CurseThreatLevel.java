package com.mycompany.missionreader.enams;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author svetl
 */
public enum CurseThreatLevel {
    HIGH,
    SPECIAL_GRADE,
    UNKNOWN;
    
    public static CurseThreatLevel fromStringToEnam(String value) { 
        if (value == null || value.isBlank()) {
            return UNKNOWN;
        }
        
        String upper = value.trim().toUpperCase();
        return switch (upper) {
            case "HIGH" -> HIGH;
            case "SPECIAL_GRADE", "SPECIAL GRADE", "SPECIAL" -> SPECIAL_GRADE;
            default -> UNKNOWN;
        };
    }
    
    @Override
    public String toString() {
        return switch (this) {
            case HIGH -> "Высокий";
            case SPECIAL_GRADE -> "Особый уровень";
            case UNKNOWN -> "Информация отсутствует";
        };
    }
}
