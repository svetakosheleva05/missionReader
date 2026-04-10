/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

/**
 *
 * @author svetl
 */
public class EconomicAssessment {
    private int totalDamageCost;
    private int infrastructureDamage;
    private int commercialDamage;
    private int transportDamage;
    private int recoveryEstimateDays;
    private boolean insuranceCovered;

    public EconomicAssessment(int totalDamageCost, int infrastructureDamage, int commercialDamage, int transportDamage, int recoveryEstimateDays, boolean insuranceCovered) {
        this.totalDamageCost = totalDamageCost;
        this.infrastructureDamage = infrastructureDamage;
        this.commercialDamage = commercialDamage;
        this.transportDamage = transportDamage;
        this.recoveryEstimateDays = recoveryEstimateDays;
        this.insuranceCovered = insuranceCovered;
    }

    public void setTotalDamageCost(int totalDamageCost) {
        this.totalDamageCost = totalDamageCost;
    }

    public void setInfrastructureDamage(int infrastructureDamage) {
        this.infrastructureDamage = infrastructureDamage;
    }

    public void setCommercialDamage(int commercialDamage) {
        this.commercialDamage = commercialDamage;
    }

    public void setTransportDamage(int transportDamage) {
        this.transportDamage = transportDamage;
    }

    public void setRecoveryEstimateDays(int recoveryEstimateDays) {
        this.recoveryEstimateDays = recoveryEstimateDays;
    }

    public void setInsuranceCovered(boolean insuranceCovered) {
        this.insuranceCovered = insuranceCovered;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (totalDamageCost > 0) {
            sb.append("Общий ущерб: ").append(totalDamageCost).append("\n");
        }

        if (infrastructureDamage > 0) {
            sb.append("Ущерб инфраструктуре: ").append(infrastructureDamage).append("\n");
        }

        if (commercialDamage > 0) {
            sb.append("Коммерческий ущерб: ").append(commercialDamage).append("\n");
        }

        if (transportDamage > 0) {
            sb.append("Ущерб транспорту: ").append(transportDamage).append("\n");
        }

        if (recoveryEstimateDays > 0) {
            sb.append("Дней на восстановление: ").append(recoveryEstimateDays).append("\n");
        }

        sb.append("Страховое покрытие: ").append(insuranceCovered ? "Да" : "Нет");

        return sb.toString();
    }
}
