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
public class ReportStrategiesContext {
    private ReportStrategy strategy;

    public ReportStrategiesContext(ReportStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ReportStrategy strategy) {
        this.strategy = strategy;
    }
    
    public Report generateReport(Mission mission) {
        return strategy.fillReport(mission);
    }
}
