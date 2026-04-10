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
public class CurseRegister {
    private List<Curse> curses = new ArrayList<>();
    
        public Curse getOrCreate(Curse curse) {
        int index = curses.indexOf(curse);
        if (index >= 0) {
            return curses.get(index);
        } else {
            curses.add(curse);
            return curse;
        }
    }
}
