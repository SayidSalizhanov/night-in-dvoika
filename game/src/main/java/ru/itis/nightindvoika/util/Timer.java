package ru.itis.nightindvoika.util;

import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;

@Data
public class Timer {
    private int hoursInNight;
    private int secondsInGameHour;
    private int currentHour;

    public Timer(int hoursInNight, int secondsInGameHour) {
        this.hoursInNight = hoursInNight;
        this.secondsInGameHour = secondsInGameHour;
        this.currentHour = 0;
    }

    public void startTimer() {
        new Thread(() -> {
            for (int i = 0; i < hoursInNight; i++) {
                try {
                    Thread.sleep(secondsInGameHour);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }

                currentHour++;
            }

            GameEngineInstance.getGameEngine().endGame();
        }).start();
    }
}
