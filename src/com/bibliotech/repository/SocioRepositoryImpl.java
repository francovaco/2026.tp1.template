package com.bibliotech.repository;

import com.bibliotech.model.Socio;
import java.util.*;

public class SocioRepositoryImpl implements ISocioRepository {
    private final Map<Integer, Socio> almacen = new HashMap<>();

    @Override
    public void guardar(Socio socio) {
        almacen.put(socio.dni(), socio);
    }

    @Override
    public Optional<Socio> buscarPorId(Integer dni) {
        return Optional.ofNullable(almacen.get(dni));
    }

    @Override
    public List<Socio> buscarTodos() {
        return new ArrayList<>(almacen.values());
    }

    @Override
    public Optional<Socio> buscarPorEmail(String email) {
        return almacen.values().stream()
                .filter(s -> s.email().equalsIgnoreCase(email))
                .findFirst();
    }
}
