package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.LibroNoDisponibleException;
import com.bibliotech.exception.LimitePrestamoException;
import com.bibliotech.exception.SocioNoEncontradoException;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.ILibroRepository;
import com.bibliotech.repository.IPrestamoRepository;
import com.bibliotech.repository.ISocioRepository;
import java.time.LocalDate;
import java.util.List;

public class PrestamoService {
    private static final int DIAS_PRESTAMO = 14;

    private final IPrestamoRepository prestamoRepo;
    private final ILibroRepository libroRepo;
    private final ISocioRepository socioRepo;

    public PrestamoService(IPrestamoRepository prestamoRepo, ILibroRepository libroRepo, ISocioRepository socioRepo) {
        this.prestamoRepo = prestamoRepo;
        this.libroRepo = libroRepo;
        this.socioRepo = socioRepo;
    }

    public void realizarPrestamo(String isbn, int dniSocio) throws BibliotecaException {
        Socio socio = socioRepo.buscarPorId(dniSocio)
                .orElseThrow(() -> new SocioNoEncontradoException(dniSocio));

        if (libroRepo.buscarPorId(isbn).isEmpty() || prestamoRepo.estaEnPrestamo(isbn)) {
            throw new LibroNoDisponibleException(isbn);
        }

        if (prestamoRepo.buscarPorDni(dniSocio).size() >= socio.maxLibros()) {
            throw new LimitePrestamoException(dniSocio, socio.maxLibros());
        }

        prestamoRepo.guardar(new Prestamo(isbn, dniSocio, LocalDate.now(), LocalDate.now().plusDays(DIAS_PRESTAMO)));
    }

    public List<Prestamo> listarPrestamos() {
        return prestamoRepo.buscarTodos();
    }

    public List<Prestamo> listarPrestamosDelSocio(int dniSocio) {
        return prestamoRepo.buscarPorDni(dniSocio);
    }
}