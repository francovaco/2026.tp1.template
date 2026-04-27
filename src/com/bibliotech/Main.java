package com.bibliotech;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.model.*;
import com.bibliotech.repository.*;
import com.bibliotech.service.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static LibroService libroService;
    private static SocioService socioService;
    private static PrestamoService prestamoService;

    public static void main(String[] args) {
        ILibroRepository libroRepo = new LibroRepositoryImpl();
        ISocioRepository socioRepo = new SocioRepositoryImpl();
        IPrestamoRepository prestamoRepo = new PrestamoRepositoryImpl();

        libroService = new LibroService(libroRepo);
        socioService = new SocioService(socioRepo);
        prestamoService = new PrestamoService(prestamoRepo, libroRepo, socioRepo);

        int opcion;
        do {
            System.out.println("\n=== BiblioTech ===");
            System.out.println("1. Gestión de libros");
            System.out.println("2. Gestión de socios");
            System.out.println("3. Gestión de préstamos");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 -> menuLibros();
                case 2 -> menuSocios();
                case 3 -> menuPrestamos();
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ── Libros ──────────────────────────────────────────────────────────────

    private static void menuLibros() {
        int opcion;
        do {
            System.out.println("\n── Libros ──");
            System.out.println("1. Registrar libro físico");
            System.out.println("2. Registrar ebook");
            System.out.println("3. Buscar por título");
            System.out.println("4. Buscar por autor");
            System.out.println("5. Buscar por categoría");
            System.out.println("6. Listar todos");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 -> registrarLibroFisico();
                case 2 -> registrarEbook();
                case 3 -> { System.out.print("Título: "); mostrarRecursos(libroService.buscarPorTitulo(scanner.nextLine().trim())); }
                case 4 -> { System.out.print("Autor: "); mostrarRecursos(libroService.buscarPorAutor(scanner.nextLine().trim())); }
                case 5 -> mostrarRecursos(libroService.buscarPorCategoria(leerCategoria()));
                case 6 -> mostrarRecursos(libroService.listarTodos());
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void registrarLibroFisico() {
        System.out.print("ISBN: ");        String isbn = scanner.nextLine().trim();
        System.out.print("Título: ");      String titulo = scanner.nextLine().trim();
        System.out.print("Autor: ");       String autor = scanner.nextLine().trim();
        System.out.print("Año: ");         int anio = leerInt();
        Categoria categoria = leerCategoria();
        System.out.print("Páginas: ");     int paginas = leerInt();
        libroService.registrar(new LibroFisico(isbn, titulo, autor, anio, categoria, paginas));
        System.out.println("Libro registrado.");
    }

    private static void registrarEbook() {
        System.out.print("ISBN: ");        String isbn = scanner.nextLine().trim();
        System.out.print("Título: ");      String titulo = scanner.nextLine().trim();
        System.out.print("Autor: ");       String autor = scanner.nextLine().trim();
        System.out.print("Año: ");         int anio = leerInt();
        Categoria categoria = leerCategoria();
        System.out.print("Formato (PDF/EPUB): "); String formato = scanner.nextLine().trim();
        libroService.registrar(new Ebook(isbn, titulo, autor, anio, categoria, formato));
        System.out.println("Ebook registrado.");
    }

    private static void mostrarRecursos(List<Recurso> recursos) {
        if (recursos.isEmpty()) { System.out.println("Sin resultados."); return; }
        recursos.forEach(r -> System.out.println("  [" + r.isbn() + "] " + r.titulo() + " - " + r.autor() + " (" + r.anio() + ") " + r.categoria()));
    }

    // ── Socios ──────────────────────────────────────────────────────────────

    private static void menuSocios() {
        int opcion;
        do {
            System.out.println("\n── Socios ──");
            System.out.println("1. Registrar estudiante");
            System.out.println("2. Registrar docente");
            System.out.println("3. Buscar por DNI");
            System.out.println("4. Listar todos");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 -> registrarSocio(false);
                case 2 -> registrarSocio(true);
                case 3 -> buscarSocio();
                case 4 -> listarSocios();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void registrarSocio(boolean esDocente) {
        try {
            System.out.print("DNI: ");    int dni = leerInt();
            System.out.print("Nombre: "); String nombre = scanner.nextLine().trim();
            System.out.print("Email: ");  String email = scanner.nextLine().trim();
            Socio socio = esDocente ? new Docente(dni, nombre, email) : new Estudiante(dni, nombre, email);
            socioService.registrar(socio);
            System.out.println((esDocente ? "Docente" : "Estudiante") + " registrado.");
        } catch (BibliotecaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void buscarSocio() {
        System.out.print("DNI: "); int dni = leerInt();
        socioService.buscarPorDni(dni).ifPresentOrElse(
            s -> System.out.println("  [" + s.dni() + "] " + s.nombre() + " | " + s.email() + " | " + s.getClass().getSimpleName() + " | Límite: " + s.maxLibros()),
            () -> System.out.println("Socio no encontrado.")
        );
    }

    private static void listarSocios() {
        List<Socio> socios = socioService.listarTodos();
        if (socios.isEmpty()) { System.out.println("No hay socios registrados."); return; }
        socios.forEach(s -> System.out.println("  [" + s.dni() + "] " + s.nombre() + " | " + s.getClass().getSimpleName()));
    }

    // ── Préstamos ────────────────────────────────────────────────────────────

    private static void menuPrestamos() {
        int opcion;
        do {
            System.out.println("\n── Préstamos ──");
            System.out.println("1. Realizar préstamo");
            System.out.println("2. Registrar devolución");
            System.out.println("3. Listar préstamos activos");
            System.out.println("4. Ver historial");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 -> realizarPrestamo();
                case 2 -> registrarDevolucion();
                case 3 -> listarPrestamos();
                case 4 -> verHistorial();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void realizarPrestamo() {
        try {
            System.out.print("ISBN: ");      String isbn = scanner.nextLine().trim();
            System.out.print("DNI socio: "); int dni = leerInt();
            prestamoService.realizarPrestamo(isbn, dni);
            System.out.println("Préstamo registrado.");
        } catch (BibliotecaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarDevolucion() {
        try {
            System.out.print("ISBN a devolver: "); String isbn = scanner.nextLine().trim();
            Devolucion d = prestamoService.registrarDevolucion(isbn);
            System.out.println("Devolución registrada. " + (d.diasRetraso() > 0 ? "Retraso: " + d.diasRetraso() + " días." : "A tiempo."));
        } catch (BibliotecaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarPrestamos() {
        List<Prestamo> prestamos = prestamoService.listarPrestamos();
        if (prestamos.isEmpty()) { System.out.println("No hay préstamos activos."); return; }
        prestamos.forEach(p -> System.out.println("  ISBN: " + p.isbn() + " | DNI: " + p.dniSocio() + " | Vence: " + p.fechaLimite()));
    }

    private static void verHistorial() {
        List<Devolucion> historial = prestamoService.listarHistorial();
        if (historial.isEmpty()) { System.out.println("No hay devoluciones registradas."); return; }
        historial.forEach(d -> System.out.println("  ISBN: " + d.isbn() + " | DNI: " + d.dniSocio() + " | Devuelto: " + d.fechaDevolucion() + (d.diasRetraso() > 0 ? " | Retraso: " + d.diasRetraso() + " días" : " | A tiempo")));
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private static int leerInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    private static Categoria leerCategoria() {
        Categoria[] valores = Categoria.values();
        System.out.println("Categorías:");
        for (int i = 0; i < valores.length; i++) {
            System.out.println("  " + (i + 1) + ". " + valores[i]);
        }
        System.out.print("Opción: ");
        int opcion;
        do { opcion = leerInt(); } while (opcion < 1 || opcion > valores.length);
        return valores[opcion - 1];
    }
}
