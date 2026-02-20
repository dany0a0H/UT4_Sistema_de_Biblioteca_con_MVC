package excepciones;

/**
 * Excepción lanzada cuando aún no se cumple el tiempo de espera para un libro.
 */
public class TiempoEsperaDeLibro extends Exception {
    /**
     * Crea la excepción con mensaje de tiempo de espera pendiente.
     */
    public TiempoEsperaDeLibro() {
        super("Aún no se ha acabado el tiempo de espera del libro que desea.");
    }

}
