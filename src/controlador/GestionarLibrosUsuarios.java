package controlador;

import modelo.Libro;
import modelo.Prestamo;
import modelo.Usuario;

import java.util.HashMap;
import java.util.HashSet;

public class GestionarLibrosUsuarios {

    public void prestarLibro(Libro libro, Usuario usuario) {

    }

    private boolean validezPrestamo(Usuario usuario) {


    }

    private boolean limitePrestamos(Usuario usuario) {
        Prestamo[] prestamo = usuario.getDisponibilidadPrestamo();

    }
}
