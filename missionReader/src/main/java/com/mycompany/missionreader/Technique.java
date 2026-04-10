/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

/**
 *
 * @author svetl
 */
public class Technique {
    private String name;
    private String type;
    private int damage;
    private String ownerName;

    public Technique(String name, String type, int damage, String ownerName) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.ownerName = ownerName;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    @Override
    public String toString() {
        return "Техника: " + name + "\nТип техники: " + type + "\nУрон техники: " + damage + "\nМаг, применивший технику: " + ownerName; 
    }
}
