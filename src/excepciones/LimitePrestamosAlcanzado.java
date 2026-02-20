package excepciones;

import modelo.Prestamo;
import modelo.Usuario;

import java.time.LocalDate;

/**
 * Excepción lanzada cuando un usuario alcanza el límite de préstamos activos.
 */
public class LimitePrestamosAlcanzado extends Exception {
    Usuario usuario;
    /**
     * Crea la excepción con los datos del usuario afectado.
     * @param usuario usuario que alcanzó el límite de préstamos.
     */
    public LimitePrestamosAlcanzado(Usuario usuario) {
        super("El usuario " + usuario.getNombre() + " de id: " + usuario.getId() + " ha alcanzado el número máximo de prestamos");
        this.usuario = usuario;
    }

    /**
     * Genera una sugerencia para liberar un espacio de préstamo.
     * @return mensaje con recomendación para el usuario.
     */
    public String sugerencia(){
        LocalDate fecha = LocalDate.now();
        Prestamo[] prestamos = usuario.getDisponibilidadPrestamo();
        Prestamo prestamoMasAntiguo = prestamos[0];

        for (int i = 0; i < prestamos.length; i++){
            if(prestamoMasAntiguo.getFechaPrestamo().isBefore(prestamos[i].getFechaPrestamo())){
                prestamoMasAntiguo = prestamos[i];
            }
        }

        String sugerencia = "El libro prestado hace más tiempo es: " + prestamoMasAntiguo.getLibro().getTitulo() + ". puede devolverlo para tener otro préstamo";
        return sugerencia;
    }

    /**
     * Obtiene el mensaje completo de la excepción con sugerencia.
     * @return mensaje detallado de la excepción.
     */
    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + sugerencia();
    }
}
