package com.bibliotech.service;

import com.bibliotech.model.Socio;
import com.bibliotech.repository.ISocioRepository;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SocioService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");

    private final ISocioRepository repositorio;

    public SocioService(ISocioRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void registrar(Socio socio) {
        if (repositorio.buscarPorId(socio.dni()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un socio con el DNI: " + socio.dni());
        }
        if (!EMAIL_PATTERN.matcher(socio.email()).matches()) {
            throw new IllegalArgumentException("El email no tiene un formato válido: " + socio.email());
        }
        repositorio.guardar(socio);
    }

    public Optional<Socio> buscarPorDni(int dni) {
        return repositorio.buscarPorId(dni);
    }

    public List<Socio> listarTodos() {
        return repositorio.buscarTodos();
    }
}
