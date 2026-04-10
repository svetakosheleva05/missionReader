/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.enams;

/**
 *
 * @author svetl
 */
public enum Visibility {
    CLEAR,
    LOW,
    POOR,
    VERY_POOR,
    NONE,
    UNKNOWN;
    
    public static Visibility fromStringToEnum(String value) {
        if (value == null || value.isBlank()) {
            return UNKNOWN;
        }
        
        String upper = value.trim().toUpperCase();
        return switch (upper) {
            case "CLEAR" -> CLEAR;
            case "LOW" -> LOW;
            case "POOR" -> POOR;
            case "VERY_POOR" -> VERY_POOR;
            case "NONE" -> NONE;
            default -> UNKNOWN;
        };
    }
    
    @Override
    public String toString() {
        return switch (this) {
            case CLEAR -> "Хорошая";
            case LOW -> "Низкая";
            case POOR -> "Плохая";
            case VERY_POOR -> "Очень плохая";
            case NONE -> "Отсутствует";
            case UNKNOWN -> "Информация отсутствует";
        };
    }
}
