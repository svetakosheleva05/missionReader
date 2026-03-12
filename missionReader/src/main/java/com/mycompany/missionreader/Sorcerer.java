/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

/**
 *
 * @author svetl
 */
public class Sorcerer {
    private String name;
    private String rank;

    public Sorcerer(String name, String rank) {
        this.name = name;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }  
    
    @Override
    public String toString() {
        return "Маг " + name + " Ранг: " + rank; 
    }
}
