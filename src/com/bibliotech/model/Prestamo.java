package com.bibliotech.model;

import java.time.LocalDate;

public record Prestamo(String isbn, int dniSocio, LocalDate fechaInicio, LocalDate fechaLimite) {}