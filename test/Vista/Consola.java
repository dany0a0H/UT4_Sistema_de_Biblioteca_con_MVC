package Vista;
import modelo.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsolaTest {

    // Verifica cuando se envía una lista vacía de libros.
    @Test
    void testMostrarLibrosVacio() {

        Consola consola = new Consola();
        List<Libro> libros = new ArrayList<>();

        String resultado = consola.mostrarLibros(libros);

        assertEquals("No se encontraron libros.\n", resultado);
    }

    // Verifica si la lista contiene libros.
    @Test
    void testMostrarLibrosConDatos() {

        Consola consola = new Consola();

        Libro libro = new Libro(
                "123",
                "El Hobbit",
                "Tolkien",
                "George Allen & Unwin",
                Genero.FANTASIA,
                3
        );
        List<Libro> libros = List.of(libro);

        String resultado = consola.mostrarLibros(libros);

        assertTrue(resultado.contains("El Hobbit"));
        assertTrue(resultado.contains("Tolkien"));
        assertTrue(resultado.contains("FANTASIA"));
    }

    // Verifica si se envía un usuario nulo.
    @Test
    void testMostrarUsuarioNull() {

        Consola consola = new Consola();

        String resultado = consola.mostrarUsuario(null);

        assertEquals("El libro no está prestado actualmente.\n", resultado);
    }

    // Verifica que mostrarUsuarios imprime correctamente la información de un usuario con un préstamo activo.
    @Test
    void testMostrarUsuariosConPrestamo() {

        Consola consola = new Consola();

        Usuario usuario = new Usuario("Carlos");

        Libro libro = new Libro(
                "111",
                "The Lion and the Unicorn",
                "Orwell",
                "Secker & Warburg",
                Genero.NOVELA,
                2
        );

        Prestamo prestamo = new Prestamo(libro, LocalDate.now());

        Prestamo[] prestamos = new Prestamo[3];
        prestamos[0] = prestamo;

        usuario.setLibrosPrestados(prestamos);

        List<Usuario> usuarios = List.of(usuario);

        String resultado = consola.mostrarUsuarios(usuarios);

        assertTrue(resultado.contains("Carlos"));
        assertTrue(resultado.contains("The Lion and the Unicorn"));
    }
}
