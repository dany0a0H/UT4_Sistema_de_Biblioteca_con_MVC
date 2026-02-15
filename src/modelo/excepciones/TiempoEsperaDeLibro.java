package modelo.excepciones;

public class TiempoEsperaDeLibro extends Exception {
    public TiempoEsperaDeLibro() {
        super("Aún no se ha acabado el tiempo de espera del libro que desea.");
    }

}
