package ru.itis.nightindvoika.entites;

import javafx.concurrent.Task;
import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.util.LoadersUtil;
import ru.itis.nightindvoika.util.PositionOnFrame;
import ru.itis.nightindvoika.util.ThreadsUtil;
import ru.itis.nightindvoika.util.Timer;

import java.io.Serializable;
import java.util.Map;

@Data
public abstract class AttackEntity implements Serializable {
    protected int startPosition;
    protected int endPosition;
    protected int currentPosition;

    protected int[] path;
    protected int currentPathIndex;
    protected Map<Integer, PositionOnFrame> positionsOnFrames; // 1 аргумент - позиция, 2 - координаты на фрейме

    private int moveCooldownInSeconds; // время между ходами
    private boolean moveAbilityStatus; // возможность двигаться в текущий момент

    private boolean maskDeception; // true - если надета маска, сущность вернётся к старту
    private boolean soundLiker; // true - сущность идет на звук, false - сущность идет от звука
    private boolean radarVisible; // true - сущность видима на радаре охранника
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
        this.moveAbilityStatus = true;
    }

    public void moveForward() {
        if (currentPosition < endPosition) {
            currentPathIndex++;
            currentPosition = path[currentPathIndex];
            moveCooldown();

            entityInOfficeCheck();
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
        if (!moveAbilityStatus) return; // если сущность уже не может двигаться, то кулдаун заново не начинается
        moveAbilityStatus = false;

        GameEngineInstance.getGameEngine().setUpdate(true);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(seconds * 1000L);
                } catch (InterruptedException e) {
                    moveAbilityStatus = true;
                    return null;
                }

                moveAbilityStatus = true;

                GameEngineInstance.getGameEngine().setUpdate(true);

                return null;
            }
        };

        Thread t = new Thread(task);
        t.start();

        ThreadsUtil.attackerThreads.put("stopThread", t);
    }

    public PositionOnFrame getCurrentPositionOnFrame() {
        return positionsOnFrames.get(currentPosition);
    }

    protected void entityInOfficeCheck() {
        if (currentPosition == endPosition) {
            GameEngine engine = GameEngineInstance.getGameEngine();
            engine.getTimer().startDeathTimer(this, engine.getDefender());
        }
    }
}
