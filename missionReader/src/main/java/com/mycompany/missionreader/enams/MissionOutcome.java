/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.enams;

/**
 *
 * @author svetl
 */

public enum MissionOutcome {
    SUCCESS,
    PARTIAL_SUCCESS,
    FAILURE,
    UNKNOWN;
    
    public static MissionOutcome fromStringToEnum(String value) {
        if (value == null || value.isBlank()) {
            return UNKNOWN;
        }
        
        String upper = value.trim().toUpperCase();
        return switch (upper) {
            case "SUCCESS" -> SUCCESS;
            case "PARTIAL_SUCCESS" -> PARTIAL_SUCCESS;
            case "FAILURE", "FAIL" -> FAILURE;
            default -> UNKNOWN;
        };
    }
    
    @Override
    public String toString() {
        return switch (this) {
            case SUCCESS -> "Успех";
            case PARTIAL_SUCCESS -> "Частичный успех";
            case FAILURE -> "Провал";
            case UNKNOWN -> "Информация отсутствует";
            default -> "Информация отсутствует";
        };
    }
}
