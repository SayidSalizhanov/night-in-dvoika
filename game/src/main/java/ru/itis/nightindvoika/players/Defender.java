package ru.itis.nightindvoika.players;

import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngine;

@Data
public class Defender {

    private GameEngine engine;

    private int currentViewPosition;

    public Defender(GameEngine engine) {
        this.engine = engine;
        this.currentViewPosition = 15;
    }

    public void displayCamera(int cameraPosition) {
        // todo
    }

    public void displayOffice() {
        engine.getOffice().display();
    }

    /*
    метод использует основной класс игры, когда атакующий игрок совершает действие,
    которое может теоретически изменить изображение на камерах или в офисе (ход, отключение света)
    */
    public void refresh() {
        // todo
    }
}
