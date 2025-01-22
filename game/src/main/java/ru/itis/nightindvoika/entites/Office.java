package ru.itis.nightindvoika.entites;

import lombok.Data;

import java.io.Serializable;

@Data
public class Office implements Serializable {
    private String sourcePath;
    private int position;

    public Office(String sourcePath, int position) {
        this.sourcePath = sourcePath;
        this.position = position;
    }
}
