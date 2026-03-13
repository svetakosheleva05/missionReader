/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author svetl
 */
public class ParserFactory {
    private List<MissionParser> parsers = new LinkedList<>();

    public ParserFactory(SorcererRegister sorcererRegister, CurseRegister curseRegister, MissionStorage missionStorage) {
        parsers.add(new TxtParser(sorcererRegister, curseRegister, missionStorage));
        parsers.add(new XmlParser(sorcererRegister, curseRegister, missionStorage));
        parsers.add(new JsonParser(sorcererRegister, curseRegister, missionStorage));
    }
    
    public MissionParser getParser(File file) {
        String fileType = getType(file);
        if (fileType.equalsIgnoreCase("txt")) {
            return parsers.get(0);
        } else if (fileType.equalsIgnoreCase("xml")) {
            return parsers.get(1);
        } else if (fileType.equalsIgnoreCase("json")) {
            return parsers.get(2);
        }
        return null;
    }
    
    public String getType(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }
}
