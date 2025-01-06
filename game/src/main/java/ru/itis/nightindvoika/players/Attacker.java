package ru.itis.nightindvoika.players;

import lombok.Data;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.mainClasses.GameEngine;

@Data
public class Attacker {

    private GameEngine engine;

    private int soundBreakCooldownInSeconds; // кулдаун: у охранника отключится возможность звука
    private int soundBreakInSeconds; // время, на которое у охранника отключится возможность звука
    private boolean soundBreakAbilityStatus; // возможность сломасть отключение звука

    private int settingDarknessStatusCooldownInSeconds; // кулдаун: можно наложить статус темноты на камеры
    private int settingDarknessStatusInSeconds; // время, на которое можно наложить статус темноты на камеры
    private boolean settingDarknessAbilityStatus; // возможность наложить статус темноты на камеры

    private int settingParalysisStatusCooldownInSeconds; // кулдаун: можно парализовать охранника
    private int settingParalysisStatusInSeconds; // время, на которое можно парализовать охранника
    private boolean settingParalysisAbilityStatus; // возможность парализовать охранника

    public Attacker(GameEngine engine, int soundBreakCooldownInSeconds, int soundBreakInSeconds, int settingDarknessStatusCooldownInSeconds, int settingDarknessStatusInSeconds, int settingParalysisStatusCooldownInSeconds, int settingParalysisStatusInSeconds) {
        this.engine = engine;
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

    public void display() {
        //todo
    }

    /*
    метод использует основной класс игры, когда атакующий игрок совершает действие,
    которое может теоретически изменить изображение на радаре (ход сущностью)
    */
    public void refresh() {
        // todo
    }

    public void moveForward(AttackEntity entity) {
        entity.moveForward();
    }

    public void moveBack(AttackEntity entity) {
        entity.moveBack();
    }

    public void soundBreakAllCameras() {
        engine.soundBreakAllCameras(soundBreakInSeconds);
        soundBreakAbilityStatus = false;

        new Thread(() -> {
            try {
                Thread.sleep(soundBreakCooldownInSeconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            soundBreakAbilityStatus = true;
        }).start();
    }

    public void setDarknessStatusOnCameras() {
        engine.breakAllCameras(settingDarknessStatusInSeconds);
        settingDarknessAbilityStatus = false;

        new Thread(() -> {
            try {
                Thread.sleep(settingDarknessStatusCooldownInSeconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            settingDarknessAbilityStatus = true;
        }).start();
    }

    public void paralyzeDefender() {
        engine.paralyzeDefender(settingParalysisStatusInSeconds);
        settingParalysisAbilityStatus = false;

        new Thread(() -> {
            try {
                Thread.sleep(settingParalysisStatusCooldownInSeconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            settingParalysisAbilityStatus = true;
        }).start();
    }
}
