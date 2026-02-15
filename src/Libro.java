 class Libro {

    private String ISBN;
    private String titulo;
    private String autor;
    private int fechaPublicacion;
    private String editorial;
    private Genero genero;

    private int cantidadTotalCopias;
    private int cantidadCopiasDisponibles;

    private EstadoLibro estado;

    public boolean estaDisponible();
    public void reducirCopia();
    public void aumentarCopia();
    public void cambiarEstado(EstadoLibro nuevoEstado);
}
