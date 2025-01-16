package ru.itis.nightindvoika.util;

import javafx.concurrent.Task;
import lombok.Data;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.players.Defender;

@Data
public class Timer {

    private int entityInOfficeDeathTimeInSeconds; // время которое сущность будет назодится в оффисе прежде

    private int hoursInNight;
    private int secondsInGameHour;
    public static int currentHour;

    public Timer(int entityInOfficeDeathTimeInSeconds, int hoursInNight, int secondsInGameHour) {
        this.entityInOfficeDeathTimeInSeconds = entityInOfficeDeathTimeInSeconds;
        this.hoursInNight = hoursInNight;
        this.secondsInGameHour = secondsInGameHour;
        currentHour = 0;
    }

    public void startGameTimer() {
        Task<Void> timerTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < hoursInNight; i++) {
                    try {
                        Thread.sleep(secondsInGameHour * 1000L);
                    } catch (InterruptedException e) {
                        return null; // ничего не делаем, так как interrupt может быть вызван только в конце игры
                    }

                    currentHour++;
                }

                GameEngineInstance.getGameEngine().endGame();
                return null;
            }
        };

        Thread t = new Thread(timerTask);
        t.start();

        ThreadsUtil.timerThreads.put("startGameTimerThread", t);
    }

    public void startDeathTimer(AttackEntity entity, Defender defender) {
        Task<Void> timerTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(entityInOfficeDeathTimeInSeconds * 1000L);
                } catch (InterruptedException e) {
                    return null; // ничего не делаем, так как interrupt может быть вызван только в конце игры
                }

                if (defender.isHoldMaskStatus() && entity.isMaskDeception()) entity.moveToStart();
                else GameEngineInstance.getGameEngine().endGame();

                return null;
            }
        };

        Thread t = new Thread(timerTask);
        t.start();

        ThreadsUtil.timerThreads.put("startDeathTimerThread", t);
    }
}
