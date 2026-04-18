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
    public String format(Report report) {
        StringBuilder sb = new StringBuilder();
        
        printBasicInfo(sb, report.getBasicInfo());
        printField(sb, "Ущерб", report.getDamageCost());
        printField(sb, "Проклятие", report.getTargetCurse());
        printList(sb, "Участники", report.getParticipants());
        printList(sb, "Использованные техники", report.getTechniques());
        printField(sb, "Экономическая оценка", report.getEconomicAssessment());
        printField(sb, "Влияние на граждан", report.getCivilianImpact());
        printField(sb, "Активность врага", report.getEnemyActivity());
        printField(sb, "Условия среды", report.getEnvironmentConditions());
        printList(sb, "Хронология", report.getTimeline());
        
        return sb.toString();
    }
    
    private void printField(StringBuilder sb, String title, String value) {
        if (value != null && !value.isEmpty()) {
            sb.append(title).append(": ").append(value).append("\n");
        }
    }
    
    private void printList(StringBuilder sb, String title, List<String> list) {
        if (list != null && !list.isEmpty()) {
            sb.append(title).append(":\n");
            for (String item : list) {
                sb.append(item).append("\n");
            }
            sb.append("\n");
        }
    }
    
    private void printBasicInfo(StringBuilder sb, Map<String, String> basicInfo) {
        if (basicInfo != null && !basicInfo.isEmpty()) {
            sb.append("=== Основная информация ===\n");
            for (Map.Entry<String, String> entry : basicInfo.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            sb.append("\n");
        }
    }
}