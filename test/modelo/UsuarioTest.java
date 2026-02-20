package modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void constructorInicializaCamposBasicos() {
        Usuario usuario = new Usuario("Ana");
        assertEquals("Ana", usuario.getNombre());
        assertTrue(usuario.getId() >= 0 && usuario.getId() <= 1_000_000);
        assertNotNull(usuario.getHistorialLibros());
        assertTrue(usuario.getHistorialLibros().isEmpty());
    }

    @Test
    void getSetIdFunciona() {
        Usuario usuario = new Usuario("Ana");
        usuario.setId(11);
        assertEquals(11, usuario.getId());
    }

    @Test
    void getSetNombreFunciona() {
        Usuario usuario = new Usuario("Ana");
        usuario.setNombre("Luis");
        assertEquals("Luis", usuario.getNombre());
    }

    @Test
    void getSetDisponibilidadPrestamoFunciona() {
        Usuario usuario = new Usuario("Ana");
        Prestamo[] prestamos = new Prestamo[2];
        usuario.setLibrosPrestados(prestamos);
        assertSame(prestamos, usuario.getDisponibilidadPrestamo());
    }

    @Test
    void getHistorialLibrosRetornaLista() {
        Usuario usuario = new Usuario("Ana");
        assertNotNull(usuario.getHistorialLibros());
    }

    @Test
    void eliminarDisponibilidadPrestamoEliminaCuandoExiste() {
        Usuario usuario = usuarioConDisponibilidad(1);
        Prestamo prestamo = crearPrestamo("001");
        usuario.getDisponibilidadPrestamo()[0] = prestamo;

        usuario.eliminarDisponibilidadPrestamo(prestamo);

        assertNull(usuario.getDisponibilidadPrestamo()[0]);
    }

    @Test
    void eliminarDisponibilidadPrestamoLanzaExcepcionCuandoNoExiste() {
        Usuario usuario = usuarioConDisponibilidad(1);
        usuario.getDisponibilidadPrestamo()[0] = crearPrestamo("001");

        assertThrows(IllegalArgumentException.class, () -> usuario.eliminarDisponibilidadPrestamo(crearPrestamo("002")));
    }

    @Test
    void getSetReservaFunciona() {
        Usuario usuario = usuarioConDisponibilidad(1);
        Prestamo reserva = crearPrestamo("010");

        usuario.setReserva(reserva);

        assertSame(reserva, usuario.getReserva());
    }

    @Test
    void reservarAsignaReservaSiNoHayReservaActual() {
        Usuario usuario = usuarioConDisponibilidad(1);
        Libro libro = crearLibro("020");
        LocalDate fecha = LocalDate.now().plusDays(4);

        usuario.reservar(libro, fecha);

        assertNotNull(usuario.getReserva());
        assertEquals(libro, usuario.getReserva().getLibro());
        assertEquals(fecha, usuario.getReserva().getFechaPrestamo());
    }

    @Test
    void reservarLanzaExcepcionSiYaExisteReserva() {
        Usuario usuario = usuarioConDisponibilidad(1);
        usuario.setReserva(crearPrestamo("020"));

        assertThrows(IllegalArgumentException.class, () -> usuario.reservar(crearLibro("021"), LocalDate.now().plusDays(1)));
    }

    private static Usuario usuarioConDisponibilidad(int slots) {
        Usuario usuario = new Usuario("Test");
        usuario.setLibrosPrestados(new Prestamo[slots]);
        return usuario;
    }

    private static Prestamo crearPrestamo(String isbn) {
        return new Prestamo(crearLibro(isbn), LocalDate.now().minusDays(2));
    }

    private static Libro crearLibro(String isbn) {
        return new Libro(isbn, "Titulo " + isbn, "Autor " + isbn, "Editorial " + isbn, Genero.NOVELA, 2);
    }
}
