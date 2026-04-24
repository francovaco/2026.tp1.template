package com.bibliotech.exception;

public class LimitePrestamoException extends BibliotecaException {
    public LimitePrestamoException(int dni, int limite) {
        super("El socio con DNI " + dni + " alcanzó el límite de " + limite + " préstamos");
    }
}