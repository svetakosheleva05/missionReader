package com.mycompany.missionreader.enams;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author svetl
 */
public enum EscalationRisk {
    NONE,
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL,
    UNKNOWN;
    
    public static EscalationRisk fromStringToEnum(String value) {
        if (value == null || value.isBlank()) {
            return UNKNOWN;
        }
        
        String upper = value.trim().toUpperCase();
        return switch (upper) {
            case "NONE" -> NONE;
            case "LOW" -> LOW;
            case "MEDIUM" -> MEDIUM;
            case "HIGH" -> HIGH;
            case "CRITICAL" -> CRITICAL;
            default -> UNKNOWN;
        };
    }
    
    @Override
    public String toString() {
        return switch (this) {
            case NONE -> "Отсутствует";
            case LOW -> "Низкий";
            case MEDIUM -> "Средний";
            case HIGH -> "Высокий";
            case CRITICAL -> "Критический";
            case UNKNOWN -> "Информация отсутствует";
        };
    }

}
