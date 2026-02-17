package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

/**
 * Representa un usuario del sistema de biblioteca.
 */
public class Usuario {

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
        Random r = new Random();
        this.id = r.nextInt(1000001);
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
     * Establece el identificador del usuario.
     * @param id nuevo identificador.
     */
    public void setId(int id) {
        this.id = id;
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
            if (this.disponibilidadPrestamo[i].equals(prestamo)) {
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
        if (this.reserva == null) {
            setReserva(new Prestamo(libro, fechaReserva));
            return;
        }
        throw new IllegalArgumentException("El usuario ya tiene el libro " + this.reserva.getLibro().getTitulo() + " reservado");
    }

}