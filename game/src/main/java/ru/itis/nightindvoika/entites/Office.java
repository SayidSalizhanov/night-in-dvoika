package ru.itis.nightindvoika.entites;

import lombok.Data;

import java.util.List;

@Data
public class Office {
    private String sourcePath;
    private int position;
    private List<AttackEntity> attackEntities;

    public Office(String sourcePath, int position, List<AttackEntity> entities) {
        this.sourcePath = sourcePath;
        this.position = position;
        this.attackEntities = entities;
    }
}
