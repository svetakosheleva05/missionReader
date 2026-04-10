/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import java.util.Objects;

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
    
    public Sorcerer(String name) {
        this.name = name;
    }
    public Sorcerer() {
        this.name = "Unknown sorcerer";
        this.rank = "Unknown sorcerer";
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
        return name + " Ранг: " + rank; 
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (getClass() != obj.getClass()) {
            return false;
        }

        Sorcerer sorcerer2 = (Sorcerer) obj;
        
        if (name == null) {
            if (sorcerer2.name != null) {
                return false;
            }
        } else if (!name.equals(sorcerer2.name)) {
            return false;
        }
        
        if (rank == null) {
            if (sorcerer2.rank != null) {
                return false;
            }
        } else if (!rank.equals(sorcerer2.rank)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.rank);
        return hash;
    }
}
