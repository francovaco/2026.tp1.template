package com.bibliotech.service;

import com.bibliotech.model.Categoria;
import com.bibliotech.model.Recurso;
import com.bibliotech.repository.ILibroRepository;
import java.util.List;

public class LibroService {
    private final ILibroRepository repositorio;

    public LibroService(ILibroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void registrar(Recurso recurso) {
        repositorio.guardar(recurso);
    }

    public List<Recurso> buscarPorTitulo(String titulo) {
        return repositorio.buscarPorTitulo(titulo);
    }

    public List<Recurso> buscarPorAutor(String autor) {
        return repositorio.buscarPorAutor(autor);
    }

    public List<Recurso> buscarPorCategoria(Categoria categoria) {
        return repositorio.buscarPorCategoria(categoria);
    }

    public List<Recurso> listarTodos() {
        return repositorio.buscarTodos();
    }
}