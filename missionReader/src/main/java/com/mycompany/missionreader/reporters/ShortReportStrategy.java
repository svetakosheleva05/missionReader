/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.reporters;

import com.mycompany.missionreader.Mission;

/**
 *
 * @author svetl
 */
public class ShortReportStrategy implements ReportStrategy {

    @Override
    public Report fillReport(Mission mission) {
        Report report = new Report();
        report.setBasicInfo(ValueFormatter.basicInfo(mission));    
        return report;
    }
    
}
