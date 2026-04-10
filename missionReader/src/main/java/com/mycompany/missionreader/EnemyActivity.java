/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import com.mycompany.missionreader.enams.EnemyMobility;
import com.mycompany.missionreader.enams.EscalationRisk;
import java.util.ArrayList;

/**
 *
 * @author svetl
 */
public class EnemyActivity {
    private String behaviorType;
    private ArrayList<String> targetPriority;
    private ArrayList<String> attackPatterns;
    private EnemyMobility mobility;
    private EscalationRisk escalationRisk;
    private ArrayList<String> countermeasuresUsed;

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    public void setTargetPriority(ArrayList<String> targetPriority) {
        this.targetPriority = targetPriority;
    }

    public void setAttackPatterns(ArrayList<String> attackPatterns) {
        this.attackPatterns = attackPatterns;
    }

    public void setMobility(EnemyMobility mobility) {
        this.mobility = mobility;
    }

    public void setEscalationRisk(EscalationRisk escalationRisk) {
        this.escalationRisk = escalationRisk;
    }
    
    public void setCountermeasuresUsed(ArrayList<String> countermeasuresUsed) {
        this.countermeasuresUsed = countermeasuresUsed;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        if (behaviorType != null && !behaviorType.isEmpty()) {
            sb.append("Тип поведения: ").append(behaviorType).append("\n");
        }
        
        if (targetPriority != null && !targetPriority.isEmpty()) {
            sb.append("Приоритеты целей: ").append(String.join(", ", targetPriority)).append("\n");
        }
        
        if (attackPatterns != null && !attackPatterns.isEmpty()) {
            sb.append("Шаблоны атак: ").append(String.join(", ", attackPatterns)).append("\n");
        }
        
        if (mobility != null) {
            sb.append("Мобильность: ").append(mobility.toString()).append("\n");
        }
        
        if (escalationRisk != null) {
            sb.append("Риск эскалации: ").append(escalationRisk.toString()).append("\n");
        }
        
        if (countermeasuresUsed != null && !countermeasuresUsed.isEmpty()) {
            sb.append("Использованные контрмеры: ").append(String.join(", ", countermeasuresUsed));
        }
        
        return sb.toString();
    }
}
