package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Representa un usuario del sistema de biblioteca.
 */
public class Usuario implements Identificable{

    int id;
    String nombre;
    public Prestamo[] disponibilidadPrestamo;
    private ArrayList<Prestamo> historialLibros;

    /**@implNote Se toma una reserva como un préstamo que se hará a futuro, reflejado en su atributo
     * fechaPrestamo */

    Prestamo reserva;


    /**
     * Crea un usuario con identificador aleatorio y nombre asignado.
     * @param nombre nombre del usuario.
     */
    public Usuario(String nombre){
        setId();
        this.nombre = nombre;
        this.historialLibros = new ArrayList<>();
    }

    /**
     * Obtiene el identificador del usuario.
     * @return identificador del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los préstamos activos del usuario.
     * @return arreglo de préstamos activos.
     */
    public Prestamo[] getDisponibilidadPrestamo() {
        return disponibilidadPrestamo;
    }

    /**
     * Asigna el arreglo de préstamos activos del usuario.
     * @param disponibilidadPrestamo arreglo de préstamos.
     */
    public void setLibrosPrestados(Prestamo[] disponibilidadPrestamo) {
        if (disponibilidadPrestamo.length != 3) {
            throw new IllegalArgumentException("No puedes insertar una lista de disponibilidad de préstamos de más de 3 elementos");
        }
        this.disponibilidadPrestamo = disponibilidadPrestamo;
    }

    /**
     * Obtiene el historial de préstamos del usuario.
     * @return lista con el historial de préstamos.
     */
    public ArrayList<Prestamo> getHistorialLibros() {
        return historialLibros;
    }

    public void eliminarDisponibilidadPrestamo(Prestamo prestamo) {
        for (int i = 0; i < this.disponibilidadPrestamo.length; i++) {
            if (this.disponibilidadPrestamo[i] == null) {
                continue;
            } else if (this.disponibilidadPrestamo[i].equals(prestamo)) {
                this.disponibilidadPrestamo[i] = null;
                return;
            }
        }
        throw new IllegalArgumentException("Prestamo no encontrado para el usuario");
    }

    public void setReserva(Prestamo reserva) {
        this.reserva = reserva;
    }

    public Prestamo getReserva() {
        return reserva;
    }

    public void reservar(Libro libro, LocalDate fechaReserva) {

        boolean libroDisponible = false;
        int index = -1;

        for (int i = 0; i < libro.getEstadoCopias().length; i++) {
            if (libro.getEstadoCopias()[i] == Estado.DISPONIBLE) {
                libroDisponible = true;
                index = i;
            }
        }

        if (this.reserva == null && libroDisponible) {
            setReserva(new Prestamo(libro, fechaReserva));
            libro.getEstadoCopias()[index] = Estado.RESERVADO;
            return;
        }
        throw new IllegalArgumentException("No se ha podido realizar la reserva del libro" );
    }

    public void anyadirAHistorial(Prestamo prestamo){
        this.historialLibros.add(prestamo);
    }

    public void setId(){
        this.id = (int)(Math.random()*100000);
    }

    public void setId(int id){
        this.id = id;
    }


}
