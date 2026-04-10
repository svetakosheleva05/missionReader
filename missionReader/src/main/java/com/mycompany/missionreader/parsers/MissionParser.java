/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.parsers;

import com.mycompany.missionreader.Mission;
import java.io.File;

/**
 *
 * @author svetl
 */
public interface MissionParser {
    
    Mission parse(File file);
    boolean canParse(File file);
    
    default String getType(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }
    
}
