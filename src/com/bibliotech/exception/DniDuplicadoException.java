package com.bibliotech.exception;

public class DniDuplicadoException extends BibliotecaException {
    public DniDuplicadoException(int dni) {
        super("Ya existe un socio con el DNI: " + dni);
    }
}
