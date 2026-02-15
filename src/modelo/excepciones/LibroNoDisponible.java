package modelo.excepciones;

import modelo.Libro;

public class LibroNoDisponible extends Exception {
    public LibroNoDisponible(Libro libro) {
        super("El libro " + libro.getTitulo() + " de ISBN " + libro.getISBN() + " no tiene copias disponibles");
    }
}
