/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author svetl
 */
public class Mission {
    private String missionId;
    private LocalDate date;
    private String location;
    private String result;
    private int damageCost;
    private Curse targetCurse;
    private List<Sorcerer> participants;
    private List<Technique> techniqueUsed;
    private String note;

    public Mission(String missionCode, LocalDate date, String location, Curse targetCurse, String result, int damageCost, List participants, List techniqueUsed, String note) {
        this.missionId = missionCode;
        this.date = date;
        this.location = location;
        this.targetCurse = targetCurse;
        this.result = result;
        this.damageCost = damageCost;
        this.participants = participants;
        this.techniqueUsed = techniqueUsed;
        this.note = note;
    }

    public String getMissionId() {
        return missionId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getResult() {
        return result;
    }

    public int getDamageCost() {
        return damageCost;
    }
    
    public Curse getTargetCurse() {
        return targetCurse;
    }

    public List getParticipants() {
        return participants;
    }

    public List getTechniqueUsed() {
        return techniqueUsed;
    }

    public String getNote() {
        return note;
    }

    public void setMissionId(String missionCode) {
        this.missionId = missionCode;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setResult(String result) {
        this.result = result;
    }

    public void setDamageCost(int damageCost) {
        this.damageCost = damageCost;
    }
    
    public void setTargetCurse(Curse targetCurse) {
        this.targetCurse = targetCurse;
    }

    public void setParticipants(List participants) {
        this.participants = participants;
    }

    public void setTechniqueUsed(List techniqueUsed) {
        this.techniqueUsed = techniqueUsed;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public void printReport() {
        System.out.println("   *** Информация о миссии " + missionId + " ***   ");
        System.out.println();
        System.out.println("ДАТА И ЛОКАЦИЯ: " + date + " " + location);
        System.out.println("-----------------------------------------------");
        System.out.println("РЕЗУЛЬТАТ: " + result);
        System.out.println("-----------------------------------------------");
        System.out.println("ПРОКЛЯТИЕ " + targetCurse.toString());
        System.out.println("-----------------------------------------------");
        System.out.println("УЧАСТНИКИ: ");
        for (Sorcerer sorcerer : participants) {
            System.out.println(sorcerer.toString());
        }
        System.out.println("-----------------------------------------------");
        System.out.println("ПРИМЕНЁННЫЕ ТЕХНИКИ: ");
        for (Technique technique : techniqueUsed)
            System.out.println(technique.toString());
        System.out.println("-----------------------------------------------");
        if (note != null) {
        System.out.println("КОММЕНТАРИИ: " + note);
        }
        System.out.println("================================================");
    }
}
