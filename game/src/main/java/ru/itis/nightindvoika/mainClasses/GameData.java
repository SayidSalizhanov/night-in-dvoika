package ru.itis.nightindvoika.mainClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.entites.Camera;
import ru.itis.nightindvoika.players.Attacker;
import ru.itis.nightindvoika.players.Defender;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class GameData implements Serializable {
    public static Attacker attacker;
    public static Defender defender;
    public static Map<String, AttackEntity> attackEntities;
    public static List<Camera> cameras;

    public void updateFromGameEngine(GameEngine gameEngine) {
        attacker = gameEngine.getAttacker();
        defender = gameEngine.getDefender();
        attackEntities = gameEngine.getAttackEntities();
        cameras = gameEngine.getCameras();
    }
}
