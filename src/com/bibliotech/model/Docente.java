package com.bibliotech.model;

public record Docente(int dni, String nombre, String email) implements Socio {
    @Override
    public int maxLibros() {
        return 5;
    }
}