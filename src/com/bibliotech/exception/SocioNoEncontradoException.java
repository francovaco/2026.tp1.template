package com.bibliotech.exception;

public class SocioNoEncontradoException extends BibliotecaException {
    public SocioNoEncontradoException(int dni) {
        super("No se encontró un socio con DNI: " + dni);
    }
}