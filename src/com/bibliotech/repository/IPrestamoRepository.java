package com.bibliotech.repository;

import com.bibliotech.model.Devolucion;
import com.bibliotech.model.Prestamo;
import java.util.List;

public interface IPrestamoRepository extends Repository<Prestamo, String> {
    List<Prestamo> buscarPorDni(int dni);
    boolean estaEnPrestamo(String isbn);
    void devolver(String isbn, Devolucion devolucion);
    List<Devolucion> buscarHistorial();
}
