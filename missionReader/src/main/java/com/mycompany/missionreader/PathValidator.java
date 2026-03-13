/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import java.io.File;

/**
 *
 * @author svetl
 */
public class PathValidator {
    public boolean isPathValid(String path) {
        if (path == null) {
            return false;
        }
        if (path.trim().isEmpty()) {
            return false;
        }

        File file = new File(path.trim());

        if (!file.exists()) {
            return false;
        }  
        if (!file.isFile()) {
            return false;
        }
        if (file.length() == 0) {
            return false;
        } 
        return true;
    }
}

        

            
