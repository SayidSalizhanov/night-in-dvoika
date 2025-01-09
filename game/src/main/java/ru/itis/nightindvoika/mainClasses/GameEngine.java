package ru.itis.nightindvoika.mainClasses;

import lombok.Data;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.entites.Camera;
import ru.itis.nightindvoika.entites.Office;
import ru.itis.nightindvoika.entites.defaultAttackEntities.Creeper;
import ru.itis.nightindvoika.entites.defaultAttackEntities.Skeleton;
import ru.itis.nightindvoika.entites.defaultAttackEntities.WitherSkeleton;
import ru.itis.nightindvoika.entites.defaultAttackEntities.Zombie;
import ru.itis.nightindvoika.players.Attacker;
import ru.itis.nightindvoika.players.Defender;

import java.util.*;

@Data
public class GameEngine {

    private Attacker attacker;
    private Defender defender;

    private Map<String, AttackEntity> attackEntities;
    private List<Camera> cameras;
    private Office office;

    private int oneGameHourInSeconds; // время игрового часа в секундах реального времени

    private int soundBreakByAttackerCooldownInSeconds; // кулдаун: атакующий может отключить звук на всех камерах
    private int soundBreakByAttackerInSeconds; // время, на которое атакующий может отключить звук на всех камерах

    private int soundBreakDefaultOneCameraCooldownInSeconds; // время, на которое отключается щвук на одной камере у охранника

    private int breakAllCamerasCooldownInSeconds; // кулдаун на отключение всех камер
    private int breakAllCamerasInSeconds; // время, на которое происходит отключение на все камеры

    private int paralysisCooldownInSeconds; // кулдаун: обездвижить охранника
    private int paralysisInSeconds; // время, на которое охранник обездвижен

    public GameEngine() {
        oneGameHourInSeconds = 90;

        soundBreakByAttackerCooldownInSeconds = 240;
        soundBreakByAttackerInSeconds = 30;

        soundBreakDefaultOneCameraCooldownInSeconds = 45;

        breakAllCamerasCooldownInSeconds = 180;
        breakAllCamerasInSeconds = 10;

        paralysisCooldownInSeconds = 400;
        paralysisInSeconds = 15;

        loadDefaultEntities();
        loadDefaultCameras();

        // todo
        office = new Office(
                "/static/images/office",
                15,
                attackEntities
        );

//        attacker = new Attacker(
//                gameEngine,
//                soundBreakByAttackerCooldownInSeconds,
//                soundBreakByAttackerInSeconds,
//                breakAllCamerasCooldownInSeconds,
//                breakAllCamerasInSeconds,
//                paralysisCooldownInSeconds,
//                paralysisInSeconds
//        );

        // todo
//        defender = new Defender(
//                gameEngine
//        );
    }

    public void startGame() {
        // todo
    }

    public void endGame() {
        // todo
    }

    public void refresh() {
        // todo
    }

    public void soundBreakAllCameras(int seconds) {
        cameras.forEach(c -> c.soundBreak(seconds));
    }

    public void breakAllCameras(int seconds) {
        cameras.forEach(c -> c.activateDarknessMode(seconds));
    }

    public void paralyzeDefender(int seconds) {
        // todo
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

        attackEntities.put("witherSkeleton", new WitherSkeleton());
        attackEntities.put("skeleton", new Skeleton());
        attackEntities.put("zombie", new Zombie());
        attackEntities.put("creeper", new Creeper());
    }

    private void loadDefaultCameras() {
        cameras = new ArrayList<>(14);

        for (int i = 1; i <= 14; i++) {
            cameras.add(
                    new Camera(
                            "/static/images/cameras/camera%d".formatted(i),
                            i,
                            soundBreakDefaultOneCameraCooldownInSeconds
                    )
            );
        }

        cameras.sort(Comparator.comparingInt(Camera::getPosition));
    }
}
