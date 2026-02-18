package Vista;

import modelo.Libro;
import modelo.Prestamo;
import modelo.Usuario;

import java.util.List;

public class Consola {

    // Mostrar lista de libros por consola.

    public void mostrarLibros(List<Libro> libros) {

        // Validación
        // Evita que imprima listas vacías
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros.");
            return;
        }

        // Recorre cada libro y muestra los datos.
        for (Libro libro : libros) {
            System.out.println("ISBN: " + libro.getISBN());
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Género: " + libro.getGenero());
            System.out.println("Estado: " + libro.getEstado());
            System.out.println("Copias disponibles: " + libro.getEstadoCopias());
            System.out.println("---------------------------");
        }
    }

    // Muestra una lista de usuarios y los libros que tienen prestados.
    public void mostrarUsuarios(List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Libros prestados:");

            // Préstamos activos.
            for (Prestamo prestamo : usuario.getDisponibilidadPrestamo()) {
                System.out.println("- " + prestamo.getLibro().getTitulo());
            }

            System.out.println("---------------------------");
        }
    }

    // Muestra si un usuario tiene actualmente un libro prestado.
    public void mostrarUsuario(Usuario usuario) {

        // null = no prestado
        if (usuario == null) {
            System.out.println("El libro no está prestado actualmente.");
        } else {
            System.out.println("El libro lo tiene: " + usuario.getNombre());
        }
    }

}
