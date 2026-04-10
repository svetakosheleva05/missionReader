/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import com.mycompany.missionreader.parsers.BinaryParser;
import com.mycompany.missionreader.parsers.MissionParser;
import com.mycompany.missionreader.parsers.XmlParser;
import com.mycompany.missionreader.parsers.TxtParser;
import com.mycompany.missionreader.parsers.JsonParser;
import com.mycompany.missionreader.parsers.TxtParser2;
import com.mycompany.missionreader.parsers.YamlParser;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author svetl
 */
public class ParserFactory {
    private List<MissionParser> parsers = new LinkedList<>();

    public ParserFactory(SorcererRegister sorcererRegister, CurseRegister curseRegister, MissionStorage missionStorage, MissionBuilder builder) {
        parsers.add(new TxtParser(sorcererRegister, curseRegister, missionStorage, builder));
        parsers.add(new XmlParser(sorcererRegister, curseRegister, missionStorage, builder));
        parsers.add(new JsonParser(sorcererRegister, curseRegister, missionStorage, builder));
        parsers.add(new YamlParser(sorcererRegister, curseRegister, missionStorage, builder));
        parsers.add(new TxtParser2(sorcererRegister, curseRegister, missionStorage, builder));
        parsers.add(new BinaryParser(sorcererRegister, curseRegister, missionStorage, builder));
    }
    
    public MissionParser getParser(File file) {
        for (MissionParser parser : parsers) {
            if (parser.canParse(file)) {
                return parser;
            }
        }
        return null;
    }
}