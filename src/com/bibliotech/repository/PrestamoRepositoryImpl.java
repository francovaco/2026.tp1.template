package com.bibliotech.repository;

import com.bibliotech.model.Devolucion;
import com.bibliotech.model.Prestamo;
import java.util.*;

public class PrestamoRepositoryImpl implements IPrestamoRepository {
    private final Map<String, Prestamo> almacen = new HashMap<>();
    private final List<Devolucion> historial = new ArrayList<>();

    @Override
    public void guardar(Prestamo prestamo) {
        almacen.put(prestamo.isbn(), prestamo);
    }

    @Override
    public Optional<Prestamo> buscarPorId(String isbn) {
        return Optional.ofNullable(almacen.get(isbn));
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return new ArrayList<>(almacen.values());
    }

    @Override
    public List<Prestamo> buscarPorDni(int dni) {
        return almacen.values().stream()
                .filter(p -> p.dniSocio() == dni)
                .toList();
    }

    @Override
    public boolean estaEnPrestamo(String isbn) {
        return almacen.containsKey(isbn);
    }

    @Override
    public void devolver(String isbn, Devolucion devolucion) {
        almacen.remove(isbn);
        historial.add(devolucion);
    }

    @Override
    public List<Devolucion> buscarHistorial() {
        return new ArrayList<>(historial);
    }
}