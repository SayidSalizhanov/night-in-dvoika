package ru.itis.nightindvoika.entites;

import lombok.Data;
import ru.itis.nightindvoika.util.PositionOnFrame;

import java.util.Map;

@Data
public abstract class AttackEntity {
    protected int startPosition;
    protected int endPosition;
    protected int currentPosition;

    protected int[] path;
    protected int currentPathIndex;
    protected Map<Integer, PositionOnFrame> positionsOnFrames; // 1 аргумент - позиция, 2 - координаты на фрейме

    private int moveCooldownInSeconds; // время между ходами
    private boolean moveAbilityStatus; // возможность двигаться в текущий момент

    private boolean maskDeception; // true - если надета маска, сущность вернётся к старту
    private boolean soundLiker; // true - сущности идет на звук, false - сущность идет от звука
    private boolean radarVisible; // true - видим на радаре охранника
    private boolean electricShockDependence; // true - может быть остановлен на время охранником при применении шока

    public AttackEntity(int[] path, int moveCooldownInSeconds, boolean maskDeception, boolean soundLiker, boolean radarVisible, boolean electricShockDependence) {
        this.startPosition = path[0];
        this.endPosition = path[path.length-1];
        this.path = path;
        this.currentPathIndex = 0;
        this.currentPosition = startPosition;
        this.moveCooldownInSeconds = moveCooldownInSeconds;
        this.maskDeception = maskDeception;
        this.soundLiker = soundLiker;
        this.radarVisible = radarVisible;
        this.electricShockDependence = electricShockDependence;
    }

    public void moveForward() {
        if (currentPosition < endPosition) {
            currentPathIndex++;
            currentPosition = path[currentPathIndex];
            moveCooldown();
        }
    }

    public void moveBack() {
        if (currentPosition > startPosition) {
            currentPathIndex--;
            currentPosition = path[currentPathIndex];
            moveCooldown();
        }
    }

    public void moveToStart() {
        currentPathIndex = 0;
        currentPosition = startPosition;
        moveCooldown();
    }

    public void moveCooldown() {
        stop(moveCooldownInSeconds);
    }

    public void stop(int seconds) {
        moveAbilityStatus = false;

        new Thread(() -> {
            try {
                Thread.sleep(seconds * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            moveAbilityStatus = true;
        }).start();
    }

    public PositionOnFrame getCurrentPositionOnFrame() {
        return positionsOnFrames.get(currentPosition);
    }
}
