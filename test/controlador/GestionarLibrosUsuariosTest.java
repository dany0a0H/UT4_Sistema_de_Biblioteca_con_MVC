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

    @Test
    void devolverLibroCasoNormalTest() {
        Usuario usuario = crearUsuario();
        Prestamo prestamoADevolver = new Prestamo(crearLibroConISBN("ISBN-DEV-1", "Devolver 1", 1), LocalDate.now().minusDays(4));
        Prestamo prestamo2 = new Prestamo(crearLibroConISBN("ISBN-DEV-2", "Devolver 2", 1), LocalDate.now().minusDays(3));
        Prestamo prestamo3 = new Prestamo(crearLibroConISBN("ISBN-DEV-3", "Devolver 3", 1), LocalDate.now().minusDays(2));
        usuario.setLibrosPrestados(new Prestamo[]{prestamoADevolver, prestamo2, prestamo3});

        GestionarLibrosUsuarios.devolverLibro(prestamoADevolver, usuario);

        Assertions.assertEquals(null, usuario.getDisponibilidadPrestamo()[0]);
        Assertions.assertEquals("ISBN-DEV-2", usuario.getDisponibilidadPrestamo()[1].getLibro().getISBN());
        Assertions.assertEquals("ISBN-DEV-3", usuario.getDisponibilidadPrestamo()[2].getLibro().getISBN());
    }

    @Test
    void devolverLibroUsuarioEquivocadoTest() {
        Usuario usuarioCorrecto = crearUsuario();
        Usuario usuarioEquivocado = crearUsuario();

        Prestamo prestamoADevolver = new Prestamo(crearLibroConISBN("ISBN-ERR-1", "Error 1", 1), LocalDate.now().minusDays(4));
        Prestamo prestamo2 = new Prestamo(crearLibroConISBN("ISBN-ERR-2", "Error 2", 1), LocalDate.now().minusDays(3));
        Prestamo prestamo3 = new Prestamo(crearLibroConISBN("ISBN-ERR-3", "Error 3", 1), LocalDate.now().minusDays(2));
        usuarioCorrecto.setLibrosPrestados(new Prestamo[]{prestamoADevolver, prestamo2, prestamo3});

        Prestamo prestamoX = new Prestamo(crearLibroConISBN("ISBN-X", "X", 1), LocalDate.now().minusDays(5));
        Prestamo prestamoY = new Prestamo(crearLibroConISBN("ISBN-Y", "Y", 1), LocalDate.now().minusDays(6));
        Prestamo prestamoZ = new Prestamo(crearLibroConISBN("ISBN-Z", "Z", 1), LocalDate.now().minusDays(7));
        usuarioEquivocado.setLibrosPrestados(new Prestamo[]{prestamoX, prestamoY, prestamoZ});

        String[] esperadoUsuarioCorrecto = contenidoDisponibilidad(usuarioCorrecto);
        String[] esperadoUsuarioEquivocado = contenidoDisponibilidad(usuarioEquivocado);

        GestionarLibrosUsuarios.devolverLibro(prestamoADevolver, usuarioEquivocado);

        Assertions.assertEquals(Arrays.toString(esperadoUsuarioCorrecto), Arrays.toString(contenidoDisponibilidad(usuarioCorrecto)));
        Assertions.assertEquals(Arrays.toString(esperadoUsuarioEquivocado), Arrays.toString(contenidoDisponibilidad(usuarioEquivocado)));
    }

    @Test
    void devolverLibroClienteNoTienePrestamoDeEseLibroTest() {
        Usuario usuario = crearUsuario();
        Prestamo prestamo1 = new Prestamo(crearLibroConISBN("ISBN-NO-1", "No 1", 1), LocalDate.now().minusDays(4));
        Prestamo prestamo2 = new Prestamo(crearLibroConISBN("ISBN-NO-2", "No 2", 1), LocalDate.now().minusDays(3));
        Prestamo prestamo3 = new Prestamo(crearLibroConISBN("ISBN-NO-3", "No 3", 1), LocalDate.now().minusDays(2));
        usuario.setLibrosPrestados(new Prestamo[]{prestamo1, prestamo2, prestamo3});

        Prestamo prestamoInexistenteParaUsuario = new Prestamo(crearLibroConISBN("ISBN-NO-1", "No 1", 1), LocalDate.now().minusDays(4));
        String[] esperado = contenidoDisponibilidad(usuario);

        GestionarLibrosUsuarios.devolverLibro(prestamoInexistenteParaUsuario, usuario);

        Assertions.assertEquals(Arrays.toString(esperado), Arrays.toString(contenidoDisponibilidad(usuario)));
    }

    @Test
    void reservarLibroCasoFuncionalTest() {
        Usuario usuario = crearUsuario();
        usuario.setLibrosPrestados(new Prestamo[]{
                new Prestamo(crearLibroConISBN("ISBN-R-1", "R1", 1), LocalDate.now().minusDays(3)),
                new Prestamo(crearLibroConISBN("ISBN-R-2", "R2", 1), LocalDate.now().minusDays(2)),
                new Prestamo(crearLibroConISBN("ISBN-R-3", "R3", 1), LocalDate.now().minusDays(1))
        });
        Libro libroAReservar = crearLibroConISBN("ISBN-RES-OK", "Reservable", 2);
        libroAReservar.getEstadoCopias()[0] = Estado.DISPONIBLE;
        libroAReservar.getEstadoCopias()[1] = Estado.PRESTADO;
        LocalDate fechaReserva = LocalDate.now().plusDays(2);

        GestionarLibrosUsuarios.reservarLibro(libroAReservar, usuario, fechaReserva);

        Assertions.assertEquals("ISBN-RES-OK", usuario.getReserva().getLibro().getISBN());
        Assertions.assertEquals(fechaReserva, usuario.getReserva().getFechaPrestamo());
    }

    @Test
    void reservarLibroSinCopiasDisponiblesTest() {
        Usuario usuario = crearUsuario();
        usuario.setLibrosPrestados(new Prestamo[]{
                new Prestamo(crearLibroConISBN("ISBN-RS-1", "RS1", 1), LocalDate.now().minusDays(3)),
                new Prestamo(crearLibroConISBN("ISBN-RS-2", "RS2", 1), LocalDate.now().minusDays(2)),
                new Prestamo(crearLibroConISBN("ISBN-RS-3", "RS3", 1), LocalDate.now().minusDays(1))
        });

        Libro libroSinDisponibilidad = crearLibroConISBN("ISBN-RES-NO", "No Reservable", 2);
        libroSinDisponibilidad.getEstadoCopias()[0] = Estado.PRESTADO;
        libroSinDisponibilidad.getEstadoCopias()[1] = Estado.RESERVADO;
        LocalDate fechaReserva = LocalDate.now().plusDays(3);

        Prestamo esperado = usuario.getReserva();

        GestionarLibrosUsuarios.reservarLibro(libroSinDisponibilidad, usuario, fechaReserva);

        Assertions.assertEquals(esperado, usuario.getReserva());
    }

    @Test
    void reservarLibroUsuarioYaTieneReservaTest() {
        Usuario usuario = crearUsuario();
        usuario.setLibrosPrestados(new Prestamo[]{
                new Prestamo(crearLibroConISBN("ISBN-RY-1", "RY1", 1), LocalDate.now().minusDays(3)),
                new Prestamo(crearLibroConISBN("ISBN-RY-2", "RY2", 1), LocalDate.now().minusDays(2)),
                new Prestamo(crearLibroConISBN("ISBN-RY-3", "RY3", 1), LocalDate.now().minusDays(1))
        });

        Prestamo reservaPrevia = new Prestamo(crearLibroConISBN("ISBN-RES-PREV", "Reserva Previa", 1), LocalDate.now().plusDays(1));
        usuario.setReserva(reservaPrevia);

        Libro nuevoLibro = crearLibroConISBN("ISBN-RES-NUEVA", "Reserva Nueva", 2);
        nuevoLibro.getEstadoCopias()[0] = Estado.DISPONIBLE;
        nuevoLibro.getEstadoCopias()[1] = Estado.DISPONIBLE;
        LocalDate fechaReserva = LocalDate.now().plusDays(4);

        GestionarLibrosUsuarios.reservarLibro(nuevoLibro, usuario, fechaReserva);

        Assertions.assertEquals("ISBN-RES-PREV", usuario.getReserva().getLibro().getISBN());
        Assertions.assertEquals(reservaPrevia.getFechaPrestamo(), usuario.getReserva().getFechaPrestamo());
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
