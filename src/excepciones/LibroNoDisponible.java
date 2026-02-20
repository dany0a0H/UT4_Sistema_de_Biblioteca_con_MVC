package excepciones;

import modelo.Libro;

/**
 * Excepción lanzada cuando no hay copias disponibles de un libro.
 */
public class LibroNoDisponible extends Exception {
    /**
     * Crea la excepción con información del libro solicitado.
     * @param libro libro sin copias disponibles.
     */
    public LibroNoDisponible(Libro libro) {
        super("El libro " + libro.getTitulo() + " de ISBN " + libro.getISBN() + " no tiene copias disponibles");
    }
}
