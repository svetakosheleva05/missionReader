package com.mycompany.missionreader.enams;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author svetl
 */
public enum TechniqueType {
    INNATE,
    SHIKIGAMI,
    BODY,
    WEAPON,
    UNKNOWN;
    
    public static TechniqueType fromStringToEnum(String value) {    
        if (value == null || value.isBlank()) {
            return UNKNOWN;
        }
        
        String upper = value.trim().toUpperCase();
        return switch (upper) {
            case "INNATE" -> INNATE;
            case "SHIKIGAMI" -> SHIKIGAMI;
            case "BODY" -> BODY;
            case "WEAPON" -> WEAPON;
            default -> UNKNOWN;
        };
    }
    
    @Override
    public String toString() {
        return switch (this) {
            case INNATE -> "Врождённая техника";
            case SHIKIGAMI -> "Техника шикигами";
            case BODY -> "Телесная техника";
            case WEAPON -> "Техника оружия";
            case UNKNOWN -> "Информация отсутствует";
        };
    }
}
