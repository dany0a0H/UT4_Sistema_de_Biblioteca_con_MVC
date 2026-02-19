package controlador;

import modelo.*;
import modelo.excepciones.LibroNoDisponible;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;


public class GestionarLibrosUsuariosTest {
    @Test
    void prestarLibroSiLimitePrestamosUsuarioAlcanzadoTest() throws LibroNoDisponible {

        Usuario usuario = crearUsuario();

        Prestamo[] prestamos = new Prestamo[3];
        prestamos = new Prestamo[]{new Prestamo(crearLibro("test", 6)), new Prestamo(crearLibro("test", 6)), new Prestamo(crearLibro("test", 6))};

        usuario.setLibrosPrestados(prestamos);

        Libro libro = crearLibro("test", 6);

        GestionarLibrosUsuarios.prestarLibro(libro, usuario);

        Assertions.assertTrue(Arrays.equals(prestamos, usuario.disponibilidadPrestamo));
    }

    @Test
    void prestarLibroSiUsuarioTienePrestamoNoDevueltoTest() throws LibroNoDisponible {
        Usuario usuario = crearUsuario();
        Prestamo prestamo1 = new Prestamo(crearLibroConISBN("ISBN-ACTIVO-1", "Activo 1", 1), LocalDate.now().minusDays(2));
        Prestamo prestamo2 = new Prestamo(crearLibroConISBN("ISBN-ACTIVO-2", "Activo 2", 1), LocalDate.now().minusDays(3));
        Prestamo prestamo3 = new Prestamo(crearLibroConISBN("ISBN-ANTIGUO", "Prestamo antiguo", 1), LocalDate.now().minusDays(45));
        usuario.setLibrosPrestados(new Prestamo[]{
                prestamo1,
                prestamo2,
                prestamo3
        });

        usuario.anyadirAHistorial(prestamo1);
        usuario.anyadirAHistorial(prestamo2);
        usuario.anyadirAHistorial(prestamo3);

        Libro libroSolicitado = crearLibroConISBN("ISBN-NUEVO-1", "Nuevo 1", 2);
        libroSolicitado.getEstadoCopias()[0] = Estado.DISPONIBLE;
        libroSolicitado.getEstadoCopias()[1] = Estado.PRESTADO;

        String[] expected = contenidoDisponibilidad(usuario);

        GestionarLibrosUsuarios.prestarLibro(libroSolicitado, usuario);

        String[] actual = contenidoDisponibilidad(usuario);
        for (int i = 0; i < expected.length; i++) {
            Assertions.assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    void prestarLibroSiNoSeAgotaTiempoEsperaLibroTest() throws LibroNoDisponible {
        Usuario usuario = crearUsuario();
        usuario.setLibrosPrestados(new Prestamo[]{
                new Prestamo(crearLibroConISBN("ISBN-ACTIVO-1", "Activo 1", 1), LocalDate.now().minusDays(3)),
                new Prestamo(crearLibroConISBN("ISBN-ACTIVO-2", "Activo 2", 1), LocalDate.now().minusDays(4)),
                new Prestamo(crearLibroConISBN("ISBN-ACTIVO-3", "Activo 3", 1), LocalDate.now().minusDays(5))
        });

        Libro libroSolicitado = crearLibroConISBN("ISBN-MISMO", "Libro Espera", 2);
        libroSolicitado.getEstadoCopias()[0] = Estado.DISPONIBLE;
        libroSolicitado.getEstadoCopias()[1] = Estado.RESERVADO;

        Prestamo mismoLibroPrestadoReciente = new Prestamo(libroSolicitado, LocalDate.now().minusDays(2));
        usuario.getHistorialLibros().add(mismoLibroPrestadoReciente);

        String[] expected = contenidoDisponibilidad(usuario);

        GestionarLibrosUsuarios.prestarLibro(libroSolicitado, usuario);

        String[] actual = contenidoDisponibilidad(usuario);
        for (int i = 0; i < expected.length; i++) {
            Assertions.assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    void prestarLibroSiNoHaySuficientesCopiasDisponiblesTest() throws LibroNoDisponible {
        Usuario usuario = crearUsuario();
        usuario.setLibrosPrestados(new Prestamo[]{
                new Prestamo(crearLibroConISBN("ISBN-ACTIVO-1", "Activo 1", 1), LocalDate.now().minusDays(2)),
                new Prestamo(crearLibroConISBN("ISBN-ACTIVO-2", "Activo 2", 1), LocalDate.now().minusDays(4)),
                new Prestamo(crearLibroConISBN("ISBN-ACTIVO-3", "Activo 3", 1), LocalDate.now().minusDays(6))
        });

        Libro libroSolicitado = crearLibroConISBN("ISBN-SIN-COPIAS", "Sin Copias", 2);
        libroSolicitado.getEstadoCopias()[0] = Estado.PRESTADO;
        libroSolicitado.getEstadoCopias()[1] = Estado.RESERVADO;

        String[] expected = contenidoDisponibilidad(usuario);

        GestionarLibrosUsuarios.prestarLibro(libroSolicitado, usuario);

        String[] actual = contenidoDisponibilidad(usuario);
        for (int i = 0; i < expected.length; i++) {
            Assertions.assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    void prestarLibroSiSeCumplenTodasLasCondicionesTest() throws LibroNoDisponible {
        Usuario usuario = crearUsuario();
        usuario.setLibrosPrestados(new Prestamo[]{
                new Prestamo(crearLibroConISBN("ISBN-ACTIVO-3", "Activo 3", 1), LocalDate.now().minusDays(2)),
                new Prestamo(crearLibroConISBN("ISBN-ACTIVO-4", "Activo 4", 1), LocalDate.now().minusDays(1)),
                new Prestamo(crearLibroConISBN("ISBN-ACTIVO-5", "Activo 5", 1), LocalDate.now().minusDays(3))
        });

        Libro libroSolicitado = crearLibroConISBN("ISBN-DISPONIBLE", "Disponible", 2);
        libroSolicitado.getEstadoCopias()[0] = Estado.DISPONIBLE;
        libroSolicitado.getEstadoCopias()[1] = Estado.PRESTADO;

        String expectedISBNPos0 = libroSolicitado.getISBN();
        String expectedISBNPos1 = usuario.getDisponibilidadPrestamo()[1].getLibro().getISBN();
        String expectedISBNPos2 = usuario.getDisponibilidadPrestamo()[2].getLibro().getISBN();

        GestionarLibrosUsuarios.prestarLibro(libroSolicitado, usuario);

        Assertions.assertEquals(expectedISBNPos0, usuario.getDisponibilidadPrestamo()[0].getLibro().getISBN());
        Assertions.assertEquals(expectedISBNPos1, usuario.getDisponibilidadPrestamo()[1].getLibro().getISBN());
        Assertions.assertEquals(expectedISBNPos2, usuario.getDisponibilidadPrestamo()[2].getLibro().getISBN());
    }

    

    public static Usuario crearUsuario(){
        return new Usuario("Test");
    }

    public static Libro crearLibro(String titulo, int copias){
        return new Libro("test", titulo, "test", "test", Genero.NOVELA, copias);

    }

    public static Libro crearLibroConISBN(String isbn, String titulo, int copias){
        return new Libro(isbn, titulo, "autor-" + isbn, "editorial-" + isbn, Genero.NOVELA, copias);
    }

    private static String[] contenidoDisponibilidad(Usuario usuario) {
        Prestamo[] prestamos = usuario.getDisponibilidadPrestamo();
        String[] contenido = new String[prestamos.length];
        for (int i = 0; i < prestamos.length; i++) {
            Prestamo prestamo = prestamos[i];
            contenido[i] = prestamo.getLibro().getISBN() + "|" + prestamo.getFechaPrestamo();
        }
        return contenido;
    }

}
