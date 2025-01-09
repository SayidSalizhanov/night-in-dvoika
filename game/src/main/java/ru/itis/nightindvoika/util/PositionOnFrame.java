package ru.itis.nightindvoika.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PositionOnFrame {
    private int x;
    private int y;

    public static PositionOnFrame create(int x, int y) {
        return new PositionOnFrame(x, y);
    }
}
