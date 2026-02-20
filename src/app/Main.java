package app;

import controlador.GestionarLibrosUsuarios;
import excepciones.LibroNoDisponible;
import modelo.ColeccionIdentificadores;
import modelo.Estado;
import modelo.Genero;
import modelo.Identificable;
import modelo.Libro;
import modelo.Prestamo;
import modelo.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static ColeccionIdentificadores identificadoresUsuarios = new ColeccionIdentificadores();
    static ColeccionIdentificadores identificadoresLibros = new ColeccionIdentificadores();
    static ColeccionIdentificadores identificadoresPrestamos = new ColeccionIdentificadores();

    public static void main(String[] args) throws LibroNoDisponible {
        boolean ejecutando = true;
        cargarLibrosDemo();

        while (ejecutando) {
            Usuario usuarioActual;
            System.out.println("Ingrese su nombre: ");
            usuarioActual = iniciarSesion();

            System.out.println("\n===== MENU BIBLIOTECA =====");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Prestar libro");
            System.out.println("3. Devolver libro");
            System.out.println("4. Reservar libro");
            System.out.println("5. Convertir reserva en prestamo");
            System.out.println("6. Cancelar reserva");
            System.out.println("7. Consultar disponibilidad de libros");
            System.out.println("8. Consultar historial de usuario");
            System.out.println("9. Listar usuarios");
            System.out.println("10. Listar libros");
            System.out.println("Seleccione una opcion numerica:");

            int opcion = obtenerOpcionMenu();

            if (opcion == 1) {
                System.out.println("\nIntroduzca por favor su nombre y apellidos: ");
                String nombre = scanner.nextLine();
                nombre = corregirNombre(nombre);

                while (nombre == null) {
                    System.out.println("Introduzca su nombre de manera adecuada. Ejemplos: Pedro Perez, Juan Gerardo Gómez Díaz");
                    nombre = corregirNombre(scanner.nextLine());
                }

                if (buscarUsuarioPorNombre(nombre) != null) {
                    System.out.println("El usuario ya existe.");
                } else {
                    Usuario nuevoUsuario = new Usuario(nombre);
                    nuevoUsuario.setLibrosPrestados(new Prestamo[3]);
                    identificadoresUsuarios.addIdentificable(nuevoUsuario);
                    System.out.println("Usuario registrado con id: " + nuevoUsuario.getId());
                }

            } else if (opcion == 2) {
                if (identificadoresLibros.getlistaIdentificable().isEmpty()) {
                    System.out.println("No hay libros registrados.");
                    continue;
                }
                Libro libro = seleccionarLibro();
                GestionarLibrosUsuarios.prestarLibro(libro, usuarioActual);
                System.out.println("Proceso de préstamo finalizado.");

            } else if (opcion == 3) {
                ArrayList<Prestamo> prestamosUsuario = prestamosActivos(usuarioActual);
                if (prestamosUsuario.isEmpty()) {
                    System.out.println("No tiene préstamos activos para devolver.");
                    continue;
                }
                System.out.println("\nSeleccione el préstamo que desea devolver:");
                for (int i = 0; i < prestamosUsuario.size(); i++) {
                    System.out.println((i + 1) + ". " + prestamosUsuario.get(i).getLibro().getTitulo());
                }
                int numero = obtenerNumero(prestamosUsuario.size());
                Prestamo prestamo = prestamosUsuario.get(numero - 1);
                GestionarLibrosUsuarios.devolverLibro(prestamo, usuarioActual);
                marcarLibroComoDisponible(prestamo.getLibro());
                System.out.println("Proceso de devolución finalizado.");

            } else if (opcion == 4) {
                if (identificadoresLibros.getlistaIdentificable().isEmpty()) {
                    System.out.println("No hay libros registrados.");
                    continue;
                }
                Libro libro = seleccionarLibro();
                System.out.println("Introduzca en cuántos días desea recogerlo (0 para hoy):");
                int dias = obtenerNumeroDesdeCero(365);
                LocalDate fechaReserva = LocalDate.now().plusDays(dias);
                GestionarLibrosUsuarios.reservarLibro(libro, usuarioActual, fechaReserva);
                System.out.println("Proceso de reserva finalizado.");

            } else if (opcion == 5) {
                GestionarLibrosUsuarios.reservaEnPrestamo(usuarioActual);
                System.out.println("Proceso de pasar reserva a préstamo finalizado.");

            } else if (opcion == 6) {
                GestionarLibrosUsuarios.cancelarReserva(usuarioActual);
                System.out.println("Reserva cancelada.");

            } else if (opcion == 7) {
                if (identificadoresLibros.getlistaIdentificable().isEmpty()) {
                    System.out.println("No hay libros registrados.");
                    continue;
                }
                System.out.println("\nDisponibilidad de libros:");
                for (Identificable identificable : identificadoresLibros.getlistaIdentificable()) {
                    Libro libro = (Libro) identificable;
                    int disponibles = 0;
                    int prestados = 0;
                    int reservados = 0;
                    for (Estado estado : libro.getEstadoCopias()) {
                        if (estado == Estado.DISPONIBLE) {
                            disponibles++;
                        } else if (estado == Estado.PRESTADO) {
                            prestados++;
                        } else if (estado == Estado.RESERVADO) {
                            reservados++;
                        }
                    }
                    System.out.println("- " + libro.getTitulo() + " | ISBN: " + libro.getISBN()
                            + " | DISP: " + disponibles + " PREST: " + prestados + " RES: " + reservados);
                }

            } else if (opcion == 8) {
                ArrayList<Prestamo> historial = usuarioActual.getHistorialLibros();
                if (historial.isEmpty()) {
                    System.out.println("No hay historial de préstamos para este usuario.");
                } else {
                    System.out.println("\nHistorial de " + usuarioActual.getNombre() + ":");
                    for (Prestamo prestamo : historial) {
                        System.out.println("- " + prestamo.getLibro().getTitulo() + " | fecha: " + prestamo.getFechaPrestamo());
                    }
                }

            } else if (opcion == 9) {
                if (identificadoresUsuarios.getlistaIdentificable().isEmpty()) {
                    System.out.println("No hay usuarios registrados.");
                } else {
                    System.out.println("\nUsuarios registrados:");
                    for (Identificable identificable : identificadoresUsuarios.getlistaIdentificable()) {
                        Usuario usuario = (Usuario) identificable;
                        System.out.println("- ID: " + usuario.getId() + " | Nombre: " + usuario.getNombre());
                    }
                }

            } else if (opcion == 10) {
                if (identificadoresLibros.getlistaIdentificable().isEmpty()) {
                    System.out.println("No hay libros registrados.");
                } else {
                    System.out.println("\nLibros registrados:");
                    for (Identificable identificable : identificadoresLibros.getlistaIdentificable()) {
                        Libro libro = (Libro) identificable;
                        System.out.println("- ID: " + libro.getId() + " | Título: " + libro.getTitulo() + " | ISBN: " + libro.getISBN());
                    }
                }
            } else {
                System.out.println("Finalizando programa.");
                ejecutando = false;
            }
        }

        scanner.close();
    }

    public static int obtenerNumero(int limite) {
        while (true) {
            try {
                if (!scanner.hasNextInt()) {
                    scanner.next();
                    System.out.println("Introduzca un valor válido, por favor");
                    continue;
                }
                int numero = scanner.nextInt();
                scanner.nextLine();
                if (numero <= limite && numero > 0) {
                    return numero;
                }
                System.out.println("Introduzca un valor válido, por favor");
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Introduzca un valor válido, por favor");
            }
        }
    }

    public static int obtenerNumeroDesdeCero(int limite) {
        while (true) {
            try {
                if (!scanner.hasNextInt()) {
                    scanner.next();
                    System.out.println("Introduzca un valor válido, por favor");
                    continue;
                }
                int numero = scanner.nextInt();
                scanner.nextLine();
                if (numero <= limite && numero >= 0) {
                    return numero;
                }
                System.out.println("Introduzca un valor válido, por favor");
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Introduzca un valor válido, por favor");
            }
        }
    }

    public static int obtenerOpcionMenu() {
        return obtenerNumero(12);
    }

    public static String corregirNombre(String nombre) {
        Pattern pattern = Pattern.compile("[A-Z][a-z]{2,} [A-Z][a-z]{2,}(\\s[A-Z][a-z]{2,})*");
        Matcher matcher = pattern.matcher(nombre);
        if (matcher.find()) {
            return nombre;
        }
        return null;
    }

    public static Usuario iniciarSesion() {
        String nombre = scanner.nextLine();
        nombre = corregirNombre(nombre);
        while (nombre == null) {
            System.out.println("Nombre inválido. Ejemplo: Pedro Perez");
            nombre = corregirNombre(scanner.nextLine());
        }

        Usuario existente = buscarUsuarioPorNombre(nombre);
        if (existente != null) {
            System.out.println("Sesión iniciada como: " + existente.getNombre());
            return existente;
        }

        Usuario nuevoUsuario = new Usuario(nombre);
        nuevoUsuario.setLibrosPrestados(new Prestamo[3]);
        identificadoresUsuarios.addIdentificable(nuevoUsuario);
        System.out.println("Usuario no encontrado. Se creó uno nuevo con id: " + nuevoUsuario.getId());
        return nuevoUsuario;
    }

    public static Usuario buscarUsuarioPorNombre(String nombre) {
        for (Identificable identificable : identificadoresUsuarios.getlistaIdentificable()) {
            Usuario usuario = (Usuario) identificable;
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario;
            }
        }
        return null;
    }

    public static Libro seleccionarLibro() {
        System.out.println("\nSeleccione el libro que desea (con un número): ");
        for (int i = 0; i < identificadoresLibros.getlistaIdentificable().size(); i++) {
            Libro libro = (Libro) identificadoresLibros.getlistaIdentificable().get(i);
            System.out.println((i + 1) + ". " + libro.getTitulo() + " | ISBN: " + libro.getISBN());
        }
        int numero = obtenerNumero(identificadoresLibros.getlistaIdentificable().size());
        return (Libro) identificadoresLibros.getlistaIdentificable().get(numero - 1);
    }

    public static ArrayList<Prestamo> prestamosActivos(Usuario usuario) {
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        for (Prestamo prestamo : usuario.getDisponibilidadPrestamo()) {
            if (prestamo != null) {
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }

    public static void marcarLibroComoDisponible(Libro libro) {
        for (int i = 0; i < libro.getEstadoCopias().length; i++) {
            if (libro.getEstadoCopias()[i] == Estado.PRESTADO || libro.getEstadoCopias()[i] == Estado.RESERVADO) {
                libro.getEstadoCopias()[i] = Estado.DISPONIBLE;
                return;
            }
        }
    }

    public static void cargarLibrosDemo() {
        if (!identificadoresLibros.getlistaIdentificable().isEmpty()) {
            return;
        }
        identificadoresLibros.addIdentificable(new Libro("9788497592208", "Cien Anos de Soledad", "Gabriel Garcia Marquez", "Debolsillo", Genero.NOVELA, 3));
        identificadoresLibros.addIdentificable(new Libro("9786073155813", "El Hobbit", "J.R.R. Tolkien", "Minotauro", Genero.FANTASIA, 2));
        identificadoresLibros.addIdentificable(new Libro("9788408237600", "Dune", "Frank Herbert", "Debolsillo", Genero.CIENCIA_FICCION, 2));
        identificadoresLibros.addIdentificable(new Libro("9788491050292", "Sapiens", "Yuval Noah Harari", "Debate", Genero.HISTORIA, 2));
    }
}
