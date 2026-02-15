package controlador;

import modelo.Estado;
import modelo.Libro;
import modelo.Prestamo;
import modelo.Usuario;
import modelo.excepciones.LibroNoDisponible;
import modelo.excepciones.LimitePrestamosAlcanzado;
import modelo.excepciones.PrestamoNoDevuelto;
import modelo.excepciones.TiempoEsperaDeLibro;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GestionarLibrosUsuarios {


    public static void prestarLibro(Libro libro, Usuario usuario) throws  LibroNoDisponible {

        try{
            boolean copiaDispoible = false;
            Estado[] disponibilidadLibro = libro.getEstadoCopias();

            for (int i = 0; i< disponibilidadLibro.length; i++ ) {
                if (disponibilidadLibro[i] == Estado.DISPONIBLE){
                    copiaDispoible = true;
                    break;
                }
            }

            if (!copiaDispoible){
                throw new LibroNoDisponible(libro);
            }

            Prestamo prestamo = new Prestamo(libro);

            if (validezPrestamo(prestamo, usuario)) {


                for (int i = 0;i < usuario.disponibilidadPrestamo.length;i++) {

                    if (usuario.disponibilidadPrestamo[i] == null) {
                        usuario.disponibilidadPrestamo[i] = prestamo;
                    }

                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean validezPrestamo(Prestamo prestamo, Usuario usuario) {
        try {
            return limitePrestamos(usuario) && tiempoEsperaLibro(prestamo, usuario) && prestamoNoDevuelto(usuario);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static boolean limitePrestamos(Usuario usuario) throws LimitePrestamosAlcanzado {
        Prestamo[] prestamos = usuario.getDisponibilidadPrestamo();
        for (int i = 0; i < prestamos.length; i++) {
            if(prestamos[i] == null){
                return true;
            }

        }
        throw new LimitePrestamosAlcanzado(usuario);
    }

    private static  boolean tiempoEsperaLibro(Prestamo prestamo, Usuario usuario) throws TiempoEsperaDeLibro {
        ArrayList<Prestamo> historialPrestamos = usuario.getHistorialLibros();

        for (int i = historialPrestamos.size(); i >= 0; i--) {

            if (prestamo.getLibro().equals(historialPrestamos.get(i).getLibro())) {

                LocalDate fechaPrestamo = historialPrestamos.get(i).getFechaPrestamo();
                LocalDate fecha = LocalDate.now();

                /* Lo que hace la siguiente sentencia es calcular la diferencia entre la fecha de la última coincidencia de Libros
                * al buscar en el historial de préstamos del usuario y la fecha en que se gestiona este nuevo préstamo
                 */
                if (ChronoUnit.DAYS.between(fechaPrestamo, fecha) <= 7) {
                    throw new TiempoEsperaDeLibro();
                }
            }
        }
        return true;
    }

    private static boolean prestamoNoDevuelto(Usuario usuario) throws PrestamoNoDevuelto {
        LocalDate fechaHoy = LocalDate.now();
        Prestamo[] prestamos = usuario.getDisponibilidadPrestamo();

        for (int i = prestamos.length; i >= 0; i--) {
            if (ChronoUnit.DAYS.between(fechaHoy, prestamos[i].getFechaPrestamo()) > 30) {
                throw new PrestamoNoDevuelto(usuario, prestamos[i]);
            }
        }
        return true;
    }

    private static Prestamo crearPrestamo(Libro libro){
        return new Prestamo(libro);
    }
}
