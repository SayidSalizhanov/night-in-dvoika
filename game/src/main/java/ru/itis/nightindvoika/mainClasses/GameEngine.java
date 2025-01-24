package ru.itis.nightindvoika.mainClasses;

import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import ru.itis.nightindvoika.action.Action;
import ru.itis.nightindvoika.action.attacker.CamerasBreakAction;
import ru.itis.nightindvoika.action.attacker.DefenderParalyzeAction;
import ru.itis.nightindvoika.action.attacker.SoundBreakAction;
import ru.itis.nightindvoika.controllers.CameraController;
import ru.itis.nightindvoika.controllers.OfficeController;
import ru.itis.nightindvoika.controllers.RadarController;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.entites.Camera;
import ru.itis.nightindvoika.entites.Office;
import ru.itis.nightindvoika.entites.defaultAttackEntities.Creeper;
import ru.itis.nightindvoika.entites.defaultAttackEntities.Skeleton;
import ru.itis.nightindvoika.entites.defaultAttackEntities.WitherSkeleton;
import ru.itis.nightindvoika.entites.defaultAttackEntities.Zombie;
import ru.itis.nightindvoika.players.Attacker;
import ru.itis.nightindvoika.players.Defender;
import ru.itis.nightindvoika.util.LoadersUtil;
import ru.itis.nightindvoika.util.ThreadsUtil;
import ru.itis.nightindvoika.util.Timer;

import java.io.Serializable;
import java.util.*;

//@Data
@Setter
@Getter
public class GameEngine implements Serializable {
    private transient LoadersUtil loadersUtil;
    private transient ThreadsUtil threadsUtil;

    private boolean update; // исключительно для сокетов

    private transient Queue<Action> actionQueue = new ArrayDeque<>(); // очередь действий

    private Attacker attacker;
    private Defender defender;

    private Map<String, AttackEntity> attackEntities;
    private List<Camera> cameras;
    private Office office;

    private int oneGameHourInSeconds; // время игрового часа в секундах реального времени
    private int hoursInNight; // часов в ночи

    private int soundBreakByAttackerCooldownInSeconds; // кулдаун: атакующий может отключить звук на всех камерах
    private int soundBreakByAttackerInSeconds; // время, на которое атакующий может отключить звук на всех камерах

    private int soundBreakDefaultOneCameraCooldownInSeconds; // время, на которое отключается звук на одной камере у охранника

    private int breakAllCamerasCooldownInSeconds; // кулдаун на отключение всех камер
    private int breakAllCamerasInSeconds; // время, на которое происходит отключение на все камеры

    private int paralysisCooldownInSeconds; // кулдаун: обездвижить охранника
    private int paralysisInSeconds; // время, на которое охранник обездвижен

    private int radarVisibleForDefenderCooldownInSeconds; // кулдаун: охранник может увидеть сущности на радаре
    private int radarVisibleInSeconds; // время, на которое охранник может посмотреть радар

    private int electricShockFromDefenderCooldownInSeconds; // кулдаун: охранник может обездвижить
    private int electricShockFromDefenderInSeconds; // время, на которое охранник может отключить движение всем аниматроникам

    private int entityInOfficeDeathTimeInSeconds; // время которое сущность стоит в офисе перед нападением

    private transient Timer timer;
    private int timeToRefreshInSeconds; // через сколько секунд сцена будет обновляться

    public GameEngine() {
        oneGameHourInSeconds = 90;
        hoursInNight = 6;

        soundBreakByAttackerCooldownInSeconds = 240;
        soundBreakByAttackerInSeconds = 30;

        soundBreakDefaultOneCameraCooldownInSeconds = 45;

        breakAllCamerasCooldownInSeconds = 180;
        breakAllCamerasInSeconds = 30;

        paralysisCooldownInSeconds = 400;
        paralysisInSeconds = 15;

        radarVisibleForDefenderCooldownInSeconds = 300;
        radarVisibleInSeconds = 10;

        electricShockFromDefenderCooldownInSeconds = 350;
        electricShockFromDefenderInSeconds = 30;

        entityInOfficeDeathTimeInSeconds = 10;

        timeToRefreshInSeconds = 1;
    }

    public void setEntitiesAndUtils() {
        loadDefaultEntities();
        loadDefaultCameras();

        office = new Office(
                "/static/images/office",
                15
        );

        attacker = new Attacker(
                soundBreakByAttackerCooldownInSeconds,
                soundBreakByAttackerInSeconds,
                breakAllCamerasCooldownInSeconds,
                breakAllCamerasInSeconds,
                paralysisCooldownInSeconds,
                paralysisInSeconds
        );
        attacker.setGameEngine(this);
        attacker.setThreadsUtil(threadsUtil);

        defender = new Defender(
                radarVisibleForDefenderCooldownInSeconds,
                radarVisibleInSeconds,
                electricShockFromDefenderCooldownInSeconds,
                electricShockFromDefenderInSeconds
        );
        defender.setGameEngine(this);
        defender.setThreadsUtil(threadsUtil);

        timer = new Timer(entityInOfficeDeathTimeInSeconds, hoursInNight, oneGameHourInSeconds, threadsUtil);
        timer.setGameEngine(this);
    }

    public void startGame() {
        timer.startGameTimer();
    }

    public void endGame(boolean defenderWinStatus) {
        Platform.runLater(() -> loadersUtil.loadEndGame(defenderWinStatus));
        GameEngineInstance.clearDataInEngine();
        threadsUtil.interruptAllThreads();
        threadsUtil.clearThreadMaps();
        stopRefreshOnControllers();
    }

    public void soundBreakAllCameras(int seconds) {
        cameras.forEach(c -> c.soundBreak(seconds));
    }

    public void breakAllCameras(int seconds) {
        cameras.forEach(c -> c.activateDarknessMode(seconds));
    }

    public void electricShockAttackEntities(int seconds) {
        for (AttackEntity entity : attackEntities.values()) {
            if (entity.isElectricShockDependence()) entity.stop(seconds);
        }
    }

    public void paralyzeDefender(int seconds) {
        defender.paralyze(seconds);

        if (!loadersUtil.primaryStage.getScene().equals(loadersUtil.scenes.get("radar"))) {
            loadersUtil.loadOffice();
        }
    }

    // метод меняют позиции сущностей если был проигран звук
    public void soundOnCamera(int position) {
        for (AttackEntity entity : attackEntities.values()) {

            int pathIndexOfPosition = -1;
            for (int i = 0; i < entity.getPath().length; i++) {
                if (entity.getPath()[i] == position) {
                    pathIndexOfPosition = i;
                    break;
                }
            }

            if (pathIndexOfPosition == -1) continue;

            switch (entity.getCurrentPathIndex() - pathIndexOfPosition) {
                case 1:
                    if (entity.isSoundLiker()) { // если сущность идет на звук
                        entity.moveBack();
                    } else {
                        entity.moveForward();
                    }
                    break;
                case 0:
                    if (!entity.isSoundLiker()) {
                        entity.moveBack();
                    }
                    break;
                case -1:
                    if (entity.isSoundLiker()) {
                        entity.moveForward();
                    } else {
                        entity.moveBack();
                    }
                    break;
            }
        }
    }

    private void loadDefaultEntities() {
        attackEntities = new HashMap<>(4);

        WitherSkeleton witherSkeleton = new WitherSkeleton();
        witherSkeleton.setGameEngine(this);
        witherSkeleton.setThreadsUtil(threadsUtil);

        Skeleton skeleton = new Skeleton();
        skeleton.setGameEngine(this);
        skeleton.setThreadsUtil(threadsUtil);

        Zombie zombie = new Zombie();
        zombie.setGameEngine(this);
        zombie.setThreadsUtil(threadsUtil);

        Creeper creeper = new Creeper();
        creeper.setGameEngine(this);
        creeper.setThreadsUtil(threadsUtil);

        attackEntities.put(witherSkeleton.getKey(), witherSkeleton);
        attackEntities.put(skeleton.getKey(), skeleton);
        attackEntities.put(zombie.getKey(), zombie);
        attackEntities.put(creeper.getKey(), creeper);
    }

    private void loadDefaultCameras() {
        cameras = new ArrayList<>(14);

        for (int i = 1; i <= 14; i++) {
            Camera camera = new Camera(
                    "/static/images/cameras/camera%d".formatted(i),
                    i,
                    soundBreakDefaultOneCameraCooldownInSeconds
            );
            camera.setGameEngine(this);
            camera.setThreadsUtil(threadsUtil);

            cameras.add(camera);
        }

        cameras.sort(Comparator.comparingInt(Camera::getPosition));
    }

    private void stopRefreshOnControllers() {
        RadarController.setRefreshFlag(false);
        OfficeController.setRefreshFlag(false);
        CameraController.setRefreshFlag(false);
    }
}
