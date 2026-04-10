/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import com.mycompany.missionreader.enams.PublicExposureRisk;

/**
 *
 * @author svetl
 */
public class CivilianImpact {
    private int evacuated;
    private int injured;
    private int missing;
    private PublicExposureRisk publicExposureRisk;

    public void setEvacuated(int evacuated) {
        this.evacuated = evacuated;
    }

    public void setInjured(int injured) {
        this.injured = injured;
    }

    public void setMissing(int missing) {
        this.missing = missing;
    }

    public void setPublicExposureRisk(PublicExposureRisk publicExposureRisk) {
        this.publicExposureRisk = publicExposureRisk;
    }
    
    @Override
    public String toString() {
        return "\n" + "Эвакуированно: " + evacuated + "\n" + "Ранено: " + injured + "\n" + "Пропало без вести: " + missing + "\n" + "Риск раскрытия: " + publicExposureRisk;                
    }
}
