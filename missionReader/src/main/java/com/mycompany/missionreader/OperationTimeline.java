/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.missionreader;

import java.time.LocalDateTime;

/**
 *
 * @author svetl
 */
public class OperationTimeline {
    private LocalDateTime timestamp;
    private String type;
    private String description;

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (timestamp != null) {
            sb.append("Время: ").append(timestamp).append("\n");
        }

        if (type != null && !type.isEmpty()) {
            sb.append("Тип: ").append(type).append("\n");
        }

        if (description != null && !description.isEmpty()) {
            sb.append("Описание: ").append(description);
        }

        return sb.toString();
    }
}
