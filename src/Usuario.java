import java.util.HashSet;

public class Usuario {

    private String id;
    private String nombre;

    private HashSet<Prestamo> prestamosActivos;
    private HashSet<Prestamo> historialPrestamos;

}
