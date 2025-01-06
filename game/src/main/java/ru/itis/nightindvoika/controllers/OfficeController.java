package ru.itis.nightindvoika.controllers;

import ru.itis.nightindvoika.mainClasses.GameEngine;

public class OfficeController {

    private GameEngine engine;

    private boolean holdMaskStatus;

    public OfficeController(GameEngine engine) {
        this.engine = engine;
        holdMaskStatus = false;
    }

    public void display() {

    }

    public void putOnMask() {
        holdMaskStatus = true;
    }

    public void takeOff() {
        holdMaskStatus = false;
    }
}
