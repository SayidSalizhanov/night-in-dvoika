package ru.itis.nightindvoika.exceptions;

public class AttackEntityMoveException extends RuntimeException {
    public AttackEntityMoveException() {
    }

    public AttackEntityMoveException(String message) {
        super(message);
    }
}
