package ru.itis.nightindvoika.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PositionOnFrame implements Serializable {
    private int x;
    private int y;

    public static PositionOnFrame create(int x, int y) {
        return new PositionOnFrame(x, y);
    }
}
