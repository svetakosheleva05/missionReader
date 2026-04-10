/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author svetl
 */
public class MissionStorage {
    private List<Mission> missions;
    
    public MissionStorage() {
        missions = new ArrayList<>();
    }
    
    public Mission getMissionWithId(String id) {
        for (Mission mission : missions) {
            if (mission.getMissionId().equals(id))
                return mission;
        }
        return null;
    }
    
    public void addMissionIfNorExists(Mission mission) {
        if (getMissionWithId(mission.getMissionId()) == null) {
        missions.add(mission);
        }
    }
    
    public void printAll() {
        System.out.println("Список миссий");
        for (Mission mission : missions) {
            System.out.println(mission.getMissionId());
        }
    }
}
