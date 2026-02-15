package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Usuario {

    int id;
    String nombre;
    public Prestamo[] disponibilidadPrestamo;
    private ArrayList<Prestamo> historialLibros;

    public Usuario(String nombre){
        Random r = new Random();
        this.id = r.nextInt(1000001);
        this.nombre = nombre;
        this.historialLibros = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Prestamo[] getDisponibilidadPrestamo() {
        return disponibilidadPrestamo;
    }

    public void setLibrosPrestados(Prestamo[] disponibilidadPrestamo) {
        this.disponibilidadPrestamo = disponibilidadPrestamo;
    }

    public ArrayList<Prestamo> getHistorialLibros() {
        return historialLibros;
    }
}