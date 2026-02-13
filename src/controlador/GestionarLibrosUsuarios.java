package controlador;

import modelo.Libro;
import modelo.Prestamo;
import modelo.Usuario;
import modelo.excepciones.LimitePrestamosAlcanzado;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GestionarLibrosUsuarios {

    public void prestarLibro(Libro libro, Usuario usuario) {

    }

    private boolean validezPrestamo(Usuario usuario) {

        
    }

    private boolean limitePrestamos(Usuario usuario) throws LimitePrestamosAlcanzado {
        Prestamo[] prestamos = usuario.getDisponibilidadPrestamo();
        for (int i = 0; i < prestamos.length; i++) {
            if(prestamos[i] == null){
                return true;
            }

        }
        throw new LimitePrestamosAlcanzado(usuario);
    }

    private boolean tiempoEsperaLibro(Libro libro, Usuario usuario) {
        ArrayList<Prestamo> historialPrestamos = usuario.getHistorialLibros();

        for (int i = historialPrestamos.size(); i >= 0; i--) {

            if (libro.equals(historialPrestamos.get(i).getLibro())) {

                LocalDate fechaPrestamo = historialPrestamos.get(i).getFechaPrestamo();
                LocalDate fecha = LocalDate.now();

                /* Lo que hace la siguiente sentencia es calcular la diferencia entre la fecha de la última coincidencia de Libros
                * al buscar en el historial de préstamos del usuario y la fecha en que se gestiona este nuevo préstamo
                 */
                if (ChronoUnit.DAYS.between(fechaPrestamo, fecha) <= 7) {
                    return false;
                }
            }
        }
        return true;
    }

}
