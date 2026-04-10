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
public class Curse {
    private String name;
    private String grade;

    public Curse(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    @Override
    public String toString() {
        return name + " Грейд: " + grade; 
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (getClass() != obj.getClass()) {
            return false;
        }

        Curse curse2 = (Curse) obj;
        
        if (name == null) {
            if (curse2.name != null) {
                return false;
            }
        } else if (!name.equals(curse2.name)) {
            return false;
        }
        
        if (grade == null) {
            if (curse2.grade != null) {
                return false;
            }
        } else if (!grade.equals(curse2.grade)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.grade);
        return hash;
    }
}
