package ru.itis.nightindvoika.players;

import javafx.concurrent.Task;
import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.util.ThreadsUtil;

import java.io.Serializable;

@Data
public class Attacker implements Serializable {

    private int soundBreakCooldownInSeconds; // кулдаун: у охранника отключится возможность звука
    private int soundBreakInSeconds; // время, на которое у охранника отключится возможность звука
    private boolean soundBreakAbilityStatus; // возможность сломасть отключение звука

    private int settingDarknessStatusCooldownInSeconds; // кулдаун: можно наложить статус темноты на камеры
    private int settingDarknessStatusInSeconds; // время, на которое можно наложить статус темноты на камеры
    private boolean settingDarknessAbilityStatus; // возможность наложить статус темноты на камеры

    private int settingParalysisStatusCooldownInSeconds; // кулдаун: можно парализовать охранника
    private int settingParalysisStatusInSeconds; // время, на которое можно парализовать охранника
    private boolean settingParalysisAbilityStatus; // возможность парализовать охранника

    public Attacker(int soundBreakCooldownInSeconds, int soundBreakInSeconds, int settingDarknessStatusCooldownInSeconds, int settingDarknessStatusInSeconds, int settingParalysisStatusCooldownInSeconds, int settingParalysisStatusInSeconds) {
        this.soundBreakCooldownInSeconds = soundBreakCooldownInSeconds;
        this.soundBreakInSeconds = soundBreakInSeconds;
        this.settingDarknessStatusCooldownInSeconds = settingDarknessStatusCooldownInSeconds;
        this.settingDarknessStatusInSeconds = settingDarknessStatusInSeconds;
        this.settingParalysisStatusCooldownInSeconds = settingParalysisStatusCooldownInSeconds;
        this.settingParalysisStatusInSeconds = settingParalysisStatusInSeconds;

        soundBreakAbilityStatus = true;
        settingDarknessAbilityStatus = true;
        settingParalysisAbilityStatus = true;
    }

    public void soundBreakAllCameras() {
        GameEngineInstance.getGameEngine().soundBreakAllCameras(soundBreakInSeconds);
        soundBreakAbilityStatus = false;

        GameEngineInstance.getGameEngine().setUpdate(true);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(soundBreakCooldownInSeconds * 1000L);
                } catch (InterruptedException e) {
                    soundBreakAbilityStatus = true;
                    return null;
                }

                soundBreakAbilityStatus = true;

                GameEngineInstance.getGameEngine().setUpdate(true);

                return null;
            }
        };

        Thread t = new Thread(task);
        t.start();

        ThreadsUtil.attackerThreads.put("soundBreakAllCamerasThread", t);
    }

    public void setDarknessStatusOnCameras() {
        GameEngineInstance.getGameEngine().breakAllCameras(settingDarknessStatusInSeconds);
        settingDarknessAbilityStatus = false;

        GameEngineInstance.getGameEngine().setUpdate(true);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(settingDarknessStatusCooldownInSeconds * 1000L);
                } catch (InterruptedException e) {
                    settingDarknessAbilityStatus = true;
                    return null;
                }

                settingDarknessAbilityStatus = true;

                GameEngineInstance.getGameEngine().setUpdate(true);

                return null;
            }
        };

        Thread t = new Thread(task);
        t.start();

        ThreadsUtil.attackerThreads.put("setDarknessStatusOnCamerasThread", t);
    }

    public void paralyzeDefender() {
        GameEngineInstance.getGameEngine().paralyzeDefender(settingParalysisStatusInSeconds);
        settingParalysisAbilityStatus = false;

        GameEngineInstance.getGameEngine().setUpdate(true);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(settingParalysisStatusCooldownInSeconds * 1000L);
                } catch (InterruptedException e) {
                    settingParalysisAbilityStatus = true;
                    return null;
                }

                settingParalysisAbilityStatus = true;

                GameEngineInstance.getGameEngine().setUpdate(true);

                return null;
            }
        };

        Thread t = new Thread(task);
        t.start();

        ThreadsUtil.attackerThreads.put("paralyzeDefenderThread", t);
    }
}
