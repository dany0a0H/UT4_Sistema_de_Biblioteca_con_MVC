import java.time.LocalDate;

public class Prestamo {

    private Libro libro;
    private Usuario usuario;

    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDevolucion;

    private boolean activo;

    public boolean estaVencido();
    public void registrarDevolucion();
    public boolean puedeReprestarse();
}
