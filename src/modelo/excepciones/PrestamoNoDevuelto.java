package modelo.excepciones;

import modelo.Prestamo;
import modelo.Usuario;

public class PrestamoNoDevuelto extends Exception {
    public PrestamoNoDevuelto(Usuario usuario, Prestamo prestamo) {

        super("El usuario " + usuario.getNombre() + " de id: " + usuario.getId() + " no ha devuelto el libro " + prestamo.getLibro().getTitulo() + " de ISBN: " +
                prestamo.getLibro().getISBN() + " prestado el día: " + prestamo.getFechaPrestamo().toString() );
    }
}
