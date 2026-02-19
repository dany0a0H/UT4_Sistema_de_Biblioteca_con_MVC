package modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PrestamoTest {

    @Test
    void constructorPorDefectoUsaFechaActual() {
        Libro libro = crearLibro("100");
        Prestamo prestamo = new Prestamo(libro);
        assertEquals(libro, prestamo.getLibro());
        assertEquals(LocalDate.now(), prestamo.getFechaPrestamo());
    }

    @Test
    void constructorConFechaUsaFechaIndicada() {
        Libro libro = crearLibro("101");
        LocalDate fecha = LocalDate.of(2026, 1, 10);
        Prestamo prestamo = new Prestamo(libro, fecha);
        assertEquals(libro, prestamo.getLibro());
        assertEquals(fecha, prestamo.getFechaPrestamo());
    }

    @Test
    void getSetLibroFunciona() {
        Prestamo prestamo = new Prestamo(crearLibro("102"));
        Libro nuevoLibro = crearLibro("103");
        prestamo.setLibro(nuevoLibro);
        assertEquals(nuevoLibro, prestamo.getLibro());
    }

    @Test
    void getSetFechaPrestamoFunciona() {
        Prestamo prestamo = new Prestamo(crearLibro("104"));
        LocalDate fecha = LocalDate.of(2025, 12, 31);
        prestamo.setFechaPrestamo(fecha);
        assertEquals(fecha, prestamo.getFechaPrestamo());
    }

    @Test
    void equalsEsReflexivo() {
        Prestamo prestamo = new Prestamo(crearLibro("105"));
        assertTrue(prestamo.equals(prestamo));
    }

    private static Libro crearLibro(String isbn) {
        return new Libro(isbn, "Titulo", "Autor", "Editorial", Genero.NOVELA, 2);
    }
}
