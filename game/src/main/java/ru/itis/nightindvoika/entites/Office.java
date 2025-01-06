package ru.itis.nightindvoika.entites;

import lombok.Data;

@Data
public class Office {

    private String sourcePath;
    private int position;

    public Office(String sourcePath, int position) {
        this.sourcePath = sourcePath;
        this.position = position;
    }
}
