package com.bibliotech.repository;

import com.bibliotech.model.Socio;
import java.util.Optional;

public interface ISocioRepository extends Repository<Socio, Integer> {
    Optional<Socio> buscarPorEmail(String email);
}
