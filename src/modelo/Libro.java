package modelo;

public class Libro {

    private String ISBN;
    String titulo;
    String autor;
    String edtorial;
    Genero genero;
    Estado[] estadoCopias;

    public Libro(String ISBN, String titulo, String autor, String edtorial,  Genero genero, int cantidadCopias) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.edtorial = edtorial;
        this.genero = genero;
        this.estadoCopias = new Estado[cantidadCopias];
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEdtorial() {
        return edtorial;
    }

    public void setEdtorial(String edtorial) {
        this.edtorial = edtorial;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Estado[] getEstadoCopias() {
        return estadoCopias;
    }

}
