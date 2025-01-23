package ru.itis.nightindvoika.entites;

import javafx.concurrent.Task;
import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.util.ThreadsUtil;

import java.io.Serializable;

@Data
public class Camera implements Serializable {

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

        GameEngineInstance.getGameEngine().setUpdate(true);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(seconds * 1000L);
                } catch (InterruptedException e) {
                    soundPlayAbilityStatus = true;
                    return null;
                }

                soundPlayAbilityStatus = true;

                GameEngineInstance.getGameEngine().setUpdate(true);

                return null;
            }
        };

        Thread t = new Thread(task);
        t.start();

        ThreadsUtil.camerasThreads.put("soundBreakThread", t);
    }

    public void activateDarknessMode(int seconds) {
        if (darknessStatus) return; // если камера уже сломана (хотя такого произойти не должно по идее), то повторно поток для кулдауна запущен не будет

        darknessStatus = true;

        GameEngineInstance.getGameEngine().setUpdate(true);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(seconds * 1000L);
                } catch (InterruptedException e) {
                    darknessStatus = false;
                    return null;
                }

                darknessStatus = false;

                GameEngineInstance.getGameEngine().setUpdate(true);

                return null;
            }
        };

        Thread t = new Thread(task);
        t.start();

        ThreadsUtil.camerasThreads.put("activateDarknessModeThread", t);
    }
}
