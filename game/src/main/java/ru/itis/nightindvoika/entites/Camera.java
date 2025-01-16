package ru.itis.nightindvoika.entites;

import javafx.concurrent.Task;
import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;

@Data
public class Camera {

    private String sourcePath;
    private int position;

    private int playSoundCooldownInSeconds; // время между звуками
    private boolean soundPlayAbilityStatus; // возможность проиграть звук в данный момент

    private boolean darknessStatus; // затемнение камеры в данный момент

    public Camera(String sourcePath, int position, int playSoundCooldownInSeconds) {
        this.sourcePath = sourcePath;
        this.position = position;
        this.playSoundCooldownInSeconds = playSoundCooldownInSeconds;

        soundPlayAbilityStatus = true;
        darknessStatus = false;
    }

    public void playSound() {
        GameEngineInstance.getGameEngine().soundOnCamera(position);
        soundBreak(playSoundCooldownInSeconds);
    }

    // кулдаун для звука
    public void soundBreak(int seconds) {
        if (!soundPlayAbilityStatus) return; // если у камеры уже нет возможности вопроизводить звук, то новый поток не запускается

        soundPlayAbilityStatus = false;

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(seconds * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                soundPlayAbilityStatus = true;
                return null;
            }
        };

        new Thread(task).start();
    }

    public void activateDarknessMode(int seconds) {
        if (darknessStatus) return; // если камера уже сломана (хотя такого произойти не должно по идее), то повторно поток для кулдауна запущен не будет

        darknessStatus = true;

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(seconds * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                darknessStatus = false;
                return null;
            }
        };

        new Thread(task).start();
    }
}
