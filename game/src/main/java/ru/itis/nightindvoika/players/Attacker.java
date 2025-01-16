package ru.itis.nightindvoika.players;

import javafx.concurrent.Task;
import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;

@Data
public class Attacker {

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

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(soundBreakCooldownInSeconds * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                soundBreakAbilityStatus = true;
                return null;
            }
        };

        new Thread(task).start();
    }

    public void setDarknessStatusOnCameras() {
        GameEngineInstance.getGameEngine().breakAllCameras(settingDarknessStatusInSeconds);
        settingDarknessAbilityStatus = false;

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(settingDarknessStatusCooldownInSeconds * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                settingDarknessAbilityStatus = true;
                return null;
            }
        };

        new Thread(task).start();
    }

    public void paralyzeDefender() {
        GameEngineInstance.getGameEngine().paralyzeDefender(settingParalysisStatusInSeconds);
        settingParalysisAbilityStatus = false;

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(settingParalysisStatusCooldownInSeconds * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                settingParalysisAbilityStatus = true;
                return null;
            }
        };

        new Thread(task).start();
    }
}
