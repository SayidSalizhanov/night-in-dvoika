package ru.itis.nightindvoika.mainClasses;

import lombok.AllArgsConstructor;
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

import java.util.List;

@Data
@AllArgsConstructor
public class GameEngine {

    private Attacker attacker;
    private Defender defender;

    private List<AttackEntity> attackEntities;
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

        soundBreakDefaultOneCameraCooldownInSeconds = 60;

        breakAllCamerasCooldownInSeconds = 180;
        breakAllCamerasInSeconds = 10;

        paralysisCooldownInSeconds = 400;
        paralysisInSeconds = 15;

        loadDefaultEntities();
        loadDefaultCameras();

        // todo
        office = new Office(
                "/static/office",
                15
        );

        attacker = new Attacker(
                this,
                soundBreakByAttackerCooldownInSeconds,
                soundBreakByAttackerInSeconds,
                breakAllCamerasCooldownInSeconds,
                breakAllCamerasInSeconds,
                paralysisCooldownInSeconds,
                paralysisInSeconds
        );

        // todo
        defender = new Defender(
                this
        );
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

    private void loadDefaultEntities() {
        attackEntities.add(new WitherSkeleton());
        attackEntities.add(new Skeleton());
        attackEntities.add(new Zombie());
        attackEntities.add(new Creeper());
    }

    private void loadDefaultCameras() {
        // todo
    }
}
