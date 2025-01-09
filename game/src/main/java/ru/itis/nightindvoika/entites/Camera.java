package ru.itis.nightindvoika.entites;

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
