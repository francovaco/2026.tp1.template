package com.bibliotech.model;

public record Estudiante(int dni, String nombre, String email) implements Socio {
    @Override
    public int maxLibros() {
        return 3;
    }
}