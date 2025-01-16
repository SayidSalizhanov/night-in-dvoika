package ru.itis.nightindvoika.players;

import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;

@Data
public class Defender {

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

        new Thread(() -> {
            try {
                Thread.sleep(radarVisibleForDefenderCooldownInSeconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            radarVisibleAbilityStatus = true;
        }).start();
    }

    public void electricShock() {
        GameEngineInstance.getGameEngine().electricShockAttackEntities(electricShockFromDefenderInSeconds);
        electricShockAbilityStatus = false;

        new Thread(() -> {
            try {
                Thread.sleep(electricShockFromDefenderCooldownInSeconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            electricShockAbilityStatus = true;
        }).start();
    }

    public void paralyze(int seconds) {
        paralyzeStatus = true;

        new Thread(() -> {
            try {
                Thread.sleep(seconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            paralyzeStatus = false;
        }).start();
    }

    public void switchMaskMode() {
        holdMaskStatus = !holdMaskStatus;
    }
}
