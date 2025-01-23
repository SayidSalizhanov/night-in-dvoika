package ru.itis.nightindvoika.players;

import javafx.concurrent.Task;
import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.util.ThreadsUtil;

import java.io.Serializable;

@Data
public class Defender implements Serializable {

    private int radarVisibleForDefenderCooldownInSeconds;
    private int radarVisibleInSeconds;
    private boolean radarVisibleAbilityStatus; // возможность посмотреть радар

    private int electricShockFromDefenderCooldownInSeconds;
    private int electricShockFromDefenderInSeconds;
    private boolean electricShockAbilityStatus; // возможность ударить током сущности

    private boolean paralyzeStatus; // true - охранник парализован

    private boolean holdMaskStatus;

    public Defender(int radarVisibleForDefenderCooldownInSeconds, int radarVisibleInSeconds, int electricShockFromDefenderCooldownInSeconds, int electricShockFromDefenderInSeconds) {
        this.radarVisibleForDefenderCooldownInSeconds = radarVisibleForDefenderCooldownInSeconds;
        this.radarVisibleInSeconds = radarVisibleInSeconds;
        this.electricShockFromDefenderCooldownInSeconds = electricShockFromDefenderCooldownInSeconds;
        this.electricShockFromDefenderInSeconds = electricShockFromDefenderInSeconds;
        radarVisibleAbilityStatus = true;
        electricShockAbilityStatus = true;
        paralyzeStatus = false;
        holdMaskStatus = false;
    }

    public void radarVisible() {
        radarVisibleAbilityStatus = false;

        GameEngineInstance.getGameEngine().setUpdate(true);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(radarVisibleForDefenderCooldownInSeconds * 1000L);
                } catch (InterruptedException e) {
                    radarVisibleAbilityStatus = true;
                    return null;
                }

                radarVisibleAbilityStatus = true;

                GameEngineInstance.getGameEngine().setUpdate(true);

                return null;
            }
        };

        Thread t = new Thread(task);
        t.start();

        ThreadsUtil.defenderThreads.put("radarVisibleThread", t);
    }

    public void electricShock() {
        GameEngineInstance.getGameEngine().electricShockAttackEntities(electricShockFromDefenderInSeconds);
        electricShockAbilityStatus = false;

        GameEngineInstance.getGameEngine().setUpdate(true);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(electricShockFromDefenderCooldownInSeconds * 1000L);
                } catch (InterruptedException e) {
                    electricShockAbilityStatus = true;
                    return null;
                }

                electricShockAbilityStatus = true;

                GameEngineInstance.getGameEngine().setUpdate(true);

                return null;
            }
        };

        Thread t = new Thread(task);
        t.start();

        ThreadsUtil.defenderThreads.put("electricShockThread", t);
    }

    public void paralyze(int seconds) {
        paralyzeStatus = true;

        GameEngineInstance.getGameEngine().setUpdate(true);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(seconds * 1000L);
                } catch (InterruptedException e) {
                    paralyzeStatus = false;
                    return null;
                }

                paralyzeStatus = false;

                GameEngineInstance.getGameEngine().setUpdate(true);

                return null;
            }
        };

        Thread t = new Thread(task);
        t.start();

        ThreadsUtil.defenderThreads.put("paralyzeThread", t);
    }

    public void switchMaskMode() {
        holdMaskStatus = !holdMaskStatus;

        GameEngineInstance.getGameEngine().setUpdate(true);
    }
}
