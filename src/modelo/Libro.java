package modelo;

/**
 * Representa un libro con sus datos bibliográficos y estado de copias.
 */
public class Libro implements Identificable{

    private int id;
    private String ISBN;
    String titulo;
    String autor;
    String edtorial;
    static Genero genero;
    Estado[] estadoCopias;

    /**
     * Crea un libro con la información principal y la cantidad de copias.
     * @param ISBN identificador ISBN del libro.
     * @param titulo título del libro.
     * @param autor autor del libro.
     * @param edtorial editorial del libro.
     * @param genero género del libro.
     * @param cantidadCopias cantidad de copias registradas.
     */
    public Libro(String ISBN, String titulo, String autor, String edtorial,  Genero genero, int cantidadCopias) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.edtorial = edtorial;
        this.genero = genero;
        this.estadoCopias = new Estado[cantidadCopias];
        estadoCopiasDefault();
    }

    private void estadoCopiasDefault(){
        for (int i = 0; i < this.estadoCopias.length; i++) {
            estadoCopias[i] = Estado.DISPONIBLE;
        }
    }

    /**
     * Obtiene el ISBN del libro.
     * @return ISBN del libro.
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Establece el ISBN del libro.
     * @param ISBN nuevo ISBN del libro.
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Obtiene el título del libro.
     * @return título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del libro.
     * @param titulo nuevo título del libro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el autor del libro.
     * @return autor del libro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Establece el autor del libro.
     * @param autor nuevo autor del libro.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Obtiene la editorial del libro.
     * @return editorial del libro.
     */
    public String getEdtorial() {
        return edtorial;
    }

    /**
     * Establece la editorial del libro.
     * @param edtorial nueva editorial del libro.
     */
    public void setEdtorial(String edtorial) {
        this.edtorial = edtorial;
    }

    /**
     * Obtiene el género del libro.
     * @return género del libro.
     */
    public static Genero getGenero() {
        return genero;
    }

    /**
     * Establece el género del libro.
     * @param genero nuevo género del libro.
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * Obtiene el estado de todas las copias del libro.
     * @return arreglo con el estado de cada copia.
     */
    public Estado[] getEstadoCopias() {
        return estadoCopias;
    }

    /**
     * Compara dos libros por su ISBN.
     * @param object objeto a comparar.
     * @return true si ambos libros tienen el mismo ISBN; false en caso contrario.
     */
    @Override
    public boolean equals(Object object){
        String thisISBN = this.getISBN();
        String ISBN = ((Libro)(object)).getISBN();
        return thisISBN.equals(ISBN);
    }

    public String getEstado() {
        return "";
    }

    public void setId(){
        this.id = (int)(Math.random()*100000);
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
}
