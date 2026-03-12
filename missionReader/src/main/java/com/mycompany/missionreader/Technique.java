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
    private String damage;
    private Sorcerer owner;

    public Technique(String name, String type, String damage) {
        this.name = name;
        this.type = type;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDamage() {
        return damage;
    }

    public Sorcerer getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public void setOwner(Sorcerer owner) {
        this.owner = owner;
    }
    
    @Override
    public String toString() {
        return "Техника: " + name + "\nТип техники: " + type + "\nУрон техники: " + damage + "\nМаг, применивший технику: " + owner; 
    }
}
