package com.bibliotech.model;

import java.time.LocalDate;

public record Devolucion(String isbn, int dniSocio, LocalDate fechaPrestamo, LocalDate fechaLimite, LocalDate fechaDevolucion, long diasRetraso) {}
