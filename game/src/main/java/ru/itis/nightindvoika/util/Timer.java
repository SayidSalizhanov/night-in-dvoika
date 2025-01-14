package ru.itis.nightindvoika.util;

import javafx.concurrent.Task;
import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;

@Data
public class Timer {
    private int hoursInNight;
    private int secondsInGameHour;
    public static int currentHour = 0;

    public Timer(int hoursInNight, int secondsInGameHour) {
        this.hoursInNight = hoursInNight;
        this.secondsInGameHour = secondsInGameHour;
    }

    public void startTimer() {
        Task<Void> timerTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < hoursInNight; i++) {
                    try {
                        Thread.sleep(secondsInGameHour * 1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException();
                    }

                    currentHour++;
                }

                GameEngineInstance.getGameEngine().endGame();
                return null;
            }
        };

        new Thread(timerTask).start();
    }
}
