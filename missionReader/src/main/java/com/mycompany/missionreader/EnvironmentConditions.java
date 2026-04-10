/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import com.mycompany.missionreader.enams.Visibility;

/**
 *
 * @author svetl
 */
public class EnvironmentConditions {
    private String weather;
    private String timeOfDay;
    private Visibility visibility;
    private int cursedEnergyDensity;

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public void setCursedEnergyDensity(int cursedEnergyDensity) {
        this.cursedEnergyDensity = cursedEnergyDensity;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (weather != null && !weather.isEmpty()) {
            sb.append("Погода: ").append(weather).append("\n");
        }

        if (timeOfDay != null && !timeOfDay.isEmpty()) {
            sb.append("Время суток: ").append(timeOfDay).append("\n");
        }

        if (visibility != null) {
            sb.append("Видимость: ").append(visibility.toString()).append("\n");
        }

        if (cursedEnergyDensity > 0) {
            sb.append("Плотность проклятой энергии: ").append(cursedEnergyDensity);
        }

        return sb.toString();
    }
}
