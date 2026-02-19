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

/**
 * Contiene operaciones para gestionar préstamos entre libros y usuarios.
 */
public class GestionarLibrosUsuarios {


    /**
     * Gestiona el préstamo de un libro para un usuario si se cumplen las validaciones.
     * @param libro libro a prestar.
     * @param usuario usuario que solicita el préstamo.
     */
    public static void prestarLibro(Libro libro, Usuario usuario) throws  LibroNoDisponible {

        try{
            int index = -1;
            boolean copiaDispoible = false;
            Estado[] disponibilidadLibro = libro.getEstadoCopias();

            for (int i = 0; i< disponibilidadLibro.length; i++ ) {
                if (disponibilidadLibro[i] == Estado.DISPONIBLE){
                    copiaDispoible = true;
                    index = i;
                    break;
                }
            }

            if (!copiaDispoible){
                throw new LibroNoDisponible(libro);
            }

            Prestamo prestamo = new Prestamo(libro);

            if (prestamoNoDevuelto(usuario) && tiempoEsperaLibro(prestamo, usuario)) {
                usuario.disponibilidadPrestamo[index] = prestamo;
                usuario.anyadirAHistorial(prestamo);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ejecuta todas las validaciones previas para registrar un préstamo.
     * @param prestamo préstamo que se desea registrar.
     * @param usuario usuario asociado al préstamo.
     * @return true si el préstamo es válido; false en caso contrario.
     */
    private static boolean validezPrestamo(Prestamo prestamo, Usuario usuario) {
        try {
            return (limitePrestamos(usuario) != null) && tiempoEsperaLibro(prestamo, usuario) && prestamoNoDevuelto(usuario);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el índice del primer espacio vacío del usuario si lo tiene
     * @param usuario usuario a validar.
     * @return el índice del espacio vacío del usuario, si lo tiene
     * @throws LimitePrestamosAlcanzado si no se encuentran espacios vacíos
     */
    private static Integer limitePrestamos(Usuario usuario) throws LimitePrestamosAlcanzado {
        Prestamo[] prestamos = usuario.getDisponibilidadPrestamo();
        for (int i = 0; i < prestamos.length; i++) {
            if(prestamos[i] == null){
                return i;
            }
        }
        throw new LimitePrestamosAlcanzado(usuario);
    }

    /**
     * Verifica el tiempo mínimo de espera para volver a prestar el mismo libro.
     * @param prestamo préstamo solicitado.
     * @param usuario usuario solicitante.
     * @return true si se cumple el tiempo de espera.
     * @throws TiempoEsperaDeLibro si el tiempo de espera de 7 días no se ha cumplido
     */
    public static  boolean tiempoEsperaLibro(Prestamo prestamo, Usuario usuario) throws TiempoEsperaDeLibro {
        ArrayList<Prestamo> historialPrestamos = usuario.getHistorialLibros();

        for (int i = historialPrestamos.size() - 1; i >= 0; i--) {

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

    /**
     * Verifica si el usuario tiene préstamos vencidos sin devolver.
     * @param usuario usuario a validar.
     * @return true si no hay préstamos vencidos sin devolución.
     * @throws PrestamoNoDevuelto si el usuario ha dejado vencer un préstamo
     */
    private static boolean prestamoNoDevuelto(Usuario usuario) throws PrestamoNoDevuelto {
        LocalDate fechaHoy = LocalDate.now();
        Prestamo[] prestamos = usuario.getDisponibilidadPrestamo();

        for (int i = prestamos.length - 1; i >= 0; i--) {
            if (Math.abs(ChronoUnit.DAYS.between(fechaHoy, prestamos[i].getFechaPrestamo())) > 30) {
                throw new PrestamoNoDevuelto(usuario, prestamos[i]);
            }
        }
        return true;
    }


    public static void devolverLibro(Prestamo prestamo, Usuario usuario) {

        try{
            usuario.eliminarDisponibilidadPrestamo(prestamo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void reservarLibro(Libro libro, Usuario usuario, LocalDate fechaReserva) {
        try{
            usuario.reservar(libro, fechaReserva);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void reservaEnPrestamo(Prestamo reserva, Usuario usuario) {
         try{
             Integer index = limitePrestamos(usuario);
             if ( index != null){
                 Prestamo auxReserva = usuario.getReserva();
                 usuario.setReserva(null);
                 usuario.disponibilidadPrestamo[index] = auxReserva;
             }
         } catch (Exception e){
             System.out.println(e.getMessage());
         }
    }

}
