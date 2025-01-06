package ru.itis.nightindvoika.entites;

import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngine;

@Data
public class Camera {

    private GameEngine engine;

    private String sourcePath;
    private int position;

    private int playSoundCooldownInSeconds; // время между звуками
    private boolean soundPlayAbilityStatus; // возможность проиграть звук в данный момент

    private boolean darknessStatus; // затемнение камеры в данный момент

    public Camera(GameEngine engine, String sourcePath, int position, int playSoundCooldownInSeconds) {
        this.engine = engine;
        this.sourcePath = sourcePath;
        this.position = position;
        this.playSoundCooldownInSeconds = playSoundCooldownInSeconds;

        soundPlayAbilityStatus = true;
        darknessStatus = false;
    }

    public void display() {
        // todo
    }

    public void playSound() {
        // todo
    }

    // кулдаун для звука
    public void soundBreak(int seconds) {
        soundPlayAbilityStatus = false;

        new Thread(() -> {
            try {
                Thread.sleep(seconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            soundPlayAbilityStatus = true;
        }).start();
    }

    public void activateDarknessMode(int seconds) {
        darknessStatus = true;

        new Thread(() -> {
            try {
                Thread.sleep(seconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            darknessStatus = false;
        }).start();
    }
}
