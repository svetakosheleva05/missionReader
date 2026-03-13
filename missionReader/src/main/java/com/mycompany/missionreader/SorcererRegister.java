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
public class SorcererRegister {
    public List<Sorcerer> sorcerers = new ArrayList<>();
    
    public Sorcerer getOrCreate(Sorcerer sorcerer) {
        int index = sorcerers.indexOf(sorcerer);
        if (index >= 0) {
            return sorcerers.get(index);
        } else {
            sorcerers.add(sorcerer);
            return sorcerer;
        }
    }
    
    public List<Sorcerer> getAll() {
        return new ArrayList<>(sorcerers);
    }
    
    public Sorcerer findSorcererByName(String name) {
        for (Sorcerer sorcerer : sorcerers) {
            if (sorcerer.getName().equals(name)) {
                return sorcerer;
            }
        }
        return null;
    }
}
