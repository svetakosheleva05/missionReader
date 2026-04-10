/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader.reporters;

import java.util.List;
import java.util.Map;

/**
 *
 * @author svetl
 */
public class TxtFormatter implements Formatter {
        
    @Override
    public String format(Report data) {
        StringBuilder sb = new StringBuilder();
        
        if (data.getBasicInfo() != null && !data.getBasicInfo().isEmpty()) {
            sb.append("=== Основная информация ===\n");
            for (Map.Entry<String, String> entry : data.getBasicInfo().entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            sb.append("\n");
        }
        
        if (data.getDamageCost() != null) {
            sb.append("Ущерб: ").append(data.getDamageCost()).append("\n\n");
        }
        
        if (data.getTargetCurse() != null) {
            sb.append("Проклятие: ").append(data.getTargetCurse()).append("\n\n");
        }
        
        List<String> participants = data.getParticipants();
        if (participants != null && !participants.isEmpty()) {
            sb.append("Участники:\n");
            for (String p : participants) {
                sb.append(" - ").append(p).append("\n");
            }
            sb.append("\n");
        }
        
        List<String> techniques = data.getTechniques();
        if (techniques != null && !techniques.isEmpty()) {
            sb.append("Использованные техники:\n");
            for (String t : techniques) {
                sb.append(" - ").append(t).append("\n");
            }
            sb.append("\n");
        }
        
        if (data.getEconomicAssessment() != null) {
            sb.append("Экономическая оценка: ").append(data.getEconomicAssessment()).append("\n\n");
        }
        
        if (data.getCivilianImpact() != null) {
            sb.append("Влияние на граждан: ").append(data.getCivilianImpact()).append("\n\n");
        }
        
        if (data.getEnemyActivity() != null) {
            sb.append("Активность врага: ").append(data.getEnemyActivity()).append("\n\n");
        }
        
        if (data.getEnvironmentConditions() != null) {
            sb.append("Условия среды: ").append(data.getEnvironmentConditions()).append("\n\n");
        }
        
        List<String> timeline = data.getTimeline();
        if (timeline != null && !timeline.isEmpty()) {
            sb.append("Хронология операции:\n");
            for (String t : timeline) {
                sb.append(" - ").append(t).append("\n");
            }
        }
        
        return sb.toString();
    }
}