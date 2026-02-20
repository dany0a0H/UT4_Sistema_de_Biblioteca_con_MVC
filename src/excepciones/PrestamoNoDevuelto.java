package excepciones;

import modelo.Prestamo;
import modelo.Usuario;

/**
 * Excepción lanzada cuando un usuario supera el plazo de devolución.
 */
public class PrestamoNoDevuelto extends Exception {
    /**
     * Crea la excepción con datos del usuario y del préstamo vencido.
     * @param usuario usuario con préstamo no devuelto.
     * @param prestamo préstamo que excede el tiempo permitido.
     */
    public PrestamoNoDevuelto(Usuario usuario, Prestamo prestamo) {

        super("El usuario " + usuario.getNombre() + " de id: " + usuario.getId() + " no ha devuelto el libro " + prestamo.getLibro().getTitulo() + " de ISBN: " +
                prestamo.getLibro().getISBN() + " prestado el día: " + prestamo.getFechaPrestamo().toString() );
    }
}
