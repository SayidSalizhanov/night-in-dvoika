package ru.itis.nightindvoika.entites;

import lombok.Data;

import java.util.Map;

@Data
public class Office {
    private String sourcePath;
    private int position;
    private Map<String, AttackEntity> attackEntities;
    private boolean holdMaskStatus;

    public Office(String sourcePath, int position, Map<String, AttackEntity> entities) {
        this.sourcePath = sourcePath;
        this.position = position;
        this.attackEntities = entities;
        this.holdMaskStatus = false;
    }

    public void switchMaskMode() {
        holdMaskStatus = !holdMaskStatus;
    }
}
