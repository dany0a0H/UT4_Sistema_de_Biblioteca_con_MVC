package modelo.excepciones;

import modelo.Prestamo;
import modelo.Usuario;

import java.time.LocalDate;

public class LimitePrestamosAlcanzado extends RuntimeException {
    Usuario usuario;
    public LimitePrestamosAlcanzado(Usuario usuario) {
        super("El usuario " + usuario.getNombre() + " de id: " + usuario.getId() + " ha alcanzado el número máximo de prestamos");
        this.usuario = usuario;
    }

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

    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + sugerencia();
    }
}
