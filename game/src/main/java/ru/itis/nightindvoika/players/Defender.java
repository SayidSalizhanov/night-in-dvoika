package ru.itis.nightindvoika.players;

import lombok.Data;

@Data
public class Defender {

    private int radarVisibleForDefenderCooldownInSeconds;
    private int radarVisibleInSeconds;
    private boolean radarVisibleStatus;

    public Defender(int radarVisibleForDefenderCooldownInSeconds, int radarVisibleInSeconds) {
        this.radarVisibleForDefenderCooldownInSeconds = radarVisibleForDefenderCooldownInSeconds;
        this.radarVisibleInSeconds = radarVisibleInSeconds;
        this.radarVisibleStatus = true;
    }

    public void radarVisible() {
        radarVisibleStatus = false;

        new Thread(() -> {
            try {
                Thread.sleep(radarVisibleForDefenderCooldownInSeconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            radarVisibleStatus = true;
        }).start();
    }
}
