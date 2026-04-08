/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.enams;

/**
 *
 * @author svetl
 */
public enum EnemyMobility {
    STATIC,
    LOW,
    MEDIUM,
    HIGH,
    EXTREME,
    UNKNOWN;
    
    public static EnemyMobility fromString(String value) {
        if (value == null || value.isBlank()) {
            return UNKNOWN;
        }
        
        String upper = value.trim().toUpperCase();
        return switch (upper) {
            case "STATIC" -> STATIC;
            case "LOW" -> LOW;
            case "MEDIUM" -> MEDIUM;
            case "HIGH" -> HIGH;
            case "EXTREME" -> EXTREME;
            default -> UNKNOWN;
        };
    }
    
    @Override
    public String toString() {
        return switch (this) {
            case STATIC -> "Неподвижный";
            case LOW -> "Низкая";
            case MEDIUM -> "Средняя";
            case HIGH -> "Высокая";
            case EXTREME -> "Экстремальная";
            case UNKNOWN -> "Информация отсутствует";
        };
    }
}
