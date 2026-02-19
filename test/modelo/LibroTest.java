package modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibroTest {

    @Test
    void constructorInicializaCampos() {
        Libro libro = new Libro("123", "Titulo", "Autor", "Editorial", Genero.FANTASIA, 3);
        assertEquals("123", libro.getISBN());
        assertEquals("Titulo", libro.getTitulo());
        assertEquals("Autor", libro.getAutor());
        assertEquals("Editorial", libro.getEdtorial());
        assertEquals(Genero.FANTASIA, libro.getGenero());
        assertEquals(3, libro.getEstadoCopias().length);
    }

    @Test
    void getSetISBNFunciona() {
        Libro libro = crearLibro("111");
        libro.setISBN("222");
        assertEquals("222", libro.getISBN());
    }

    @Test
    void getSetTituloFunciona() {
        Libro libro = crearLibro("111");
        libro.setTitulo("Nuevo");
        assertEquals("Nuevo", libro.getTitulo());
    }

    @Test
    void getSetAutorFunciona() {
        Libro libro = crearLibro("111");
        libro.setAutor("Nuevo Autor");
        assertEquals("Nuevo Autor", libro.getAutor());
    }

    @Test
    void getSetEditorialFunciona() {
        Libro libro = crearLibro("111");
        libro.setEdtorial("Nueva Editorial");
        assertEquals("Nueva Editorial", libro.getEdtorial());
    }

    @Test
    void getSetGeneroFunciona() {
        Libro libro = crearLibro("111");
        libro.setGenero(Genero.CIENCIA_FICCION);
        assertEquals(Genero.CIENCIA_FICCION, libro.getGenero());
    }

    @Test
    void getEstadoCopiasRetornaArreglo() {
        Libro libro = crearLibro("111");
        assertNotNull(libro.getEstadoCopias());
    }

    @Test
    void equalsRetornaTrueParaMismoISBN() {
        Libro a = crearLibro("500");
        Libro b = crearLibro("500");
        assertTrue(a.equals(b));
    }

    @Test
    void equalsRetornaFalseParaDistintoISBN() {
        Libro a = crearLibro("500");
        Libro b = crearLibro("501");
        assertFalse(a.equals(b));
    }

    private static Libro crearLibro(String isbn) {
        return new Libro(isbn, "Titulo", "Autor", "Editorial", Genero.NOVELA, 2);
    }
}
