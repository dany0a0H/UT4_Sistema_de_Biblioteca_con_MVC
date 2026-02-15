package modelo;

import java.time.LocalDate;

public class Prestamo {

    private Libro libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaPrestamoVencimiento;

    public Prestamo(Libro libro ) {
        this.libro = libro;
        this.fechaPrestamo = LocalDate.now();
        this.fechaPrestamoVencimiento = fechaPrestamo.plusDays(30);
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
}
