package ru.itis.nightindvoika.mainClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.entites.Camera;
import ru.itis.nightindvoika.players.Attacker;
import ru.itis.nightindvoika.players.Defender;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameData implements Serializable {
    private Attacker attacker;
    private Defender defender;
    private Map<String, AttackEntity> attackEntities;
    private List<Camera> cameras;

    private boolean update; // true - что-то было добавлено или изменено, false - ничего не менялось (исключительно для сокетов)

    public synchronized void updateFromGameEngine(GameEngine gameEngine) {
        attacker = gameEngine.getAttacker();
        defender = gameEngine.getDefender();
        attackEntities = gameEngine.getAttackEntities();
        cameras = gameEngine.getCameras();
    }
}
