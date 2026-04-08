package com.mycompany.missionreader.enams;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author svetl
 */
public enum SorcererRank {
    GRADE_2,
    GRADE_1,
    SPECIAL_GRADE,
    UNKNOWN;
    
    public static SorcererRank fromStringToEnum(String value) {
        if (value == null || value.isBlank()) {
            return UNKNOWN;
        }
        String upper = value.trim().toUpperCase();
        return switch (upper) {
            case "GRADE_2" -> GRADE_2;
            case "GRADE_1" -> GRADE_1;
            case "SPECIAL_GRADE", "SPECIAL GRADE", "SPECIAL" -> SPECIAL_GRADE;
            default -> UNKNOWN;
        };
    }
    
    @Override
    public String toString() {
        return switch (this) {
            case GRADE_2 -> "2-й ранг";
            case GRADE_1 -> "1-й ранг";
            case SPECIAL_GRADE -> "Особый ранг";
            case UNKNOWN -> "Информация отсутствует";
        };
    }
}
