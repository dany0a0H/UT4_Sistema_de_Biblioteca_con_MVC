package app;

import controlador.*;
import excepciones.LibroNoDisponible;
import modelo.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {


    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws LibroNoDisponible {
        boolean ejecutando = true;
        ColeccionIdentificadores identificadoresUsuarios = new ColeccionIdentificadores();
        ColeccionIdentificadores identificadoresLibros = new ColeccionIdentificadores();
        ColeccionIdentificadores identificadoresPrestamos = new ColeccionIdentificadores();

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

                while (nombre == null){
                    System.out.println("Introduzca su nombre de manera adecuada. Ejemplos: Pedro Perez, Juan Gerardo Gómez Díaz");
                    nombre = corregirNombre(scanner.nextLine());
                }

                identificadoresUsuarios.addIdentificable(new Usuario(nombre));

            } else if (opcion == 2) {
                System.out.println("\nSeleccione el libro que desea (con un número): ");
                int i = 0;
                for (Identificable libro : identificadoresLibros.getlistaIdentificable()){
                    System.out.println(i+1 + ". " + ((Libro)(libro)).getTitulo());
                    i++;
                }
                int numero = obtenerNumero(identificadoresLibros.getlistaIdentificable().size() - 1);
                GestionarLibrosUsuarios.prestarLibro((Libro)(identificadoresLibros.getlistaIdentificable().get(numero)), usuarioActual);
                
            } else if (opcion == 3) {
                // TODO: Prestar libro
            } else if (opcion == 4) {
                // TODO: Devolver libro
            } else if (opcion == 5) {
                // TODO: Reservar libro
            } else if (opcion == 6) {
                // TODO: Convertir reserva en prestamo
            } else if (opcion == 7) {
                // TODO: Cancelar reserva
            } else if (opcion == 8) {
                // TODO: Consultar disponibilidad de libros
            } else if (opcion == 9) {
                // TODO: Consultar historial de usuario
            } else if (opcion == 10) {
                // TODO: Listar usuarios
            } else {
                System.out.println("Finalizando programa.");
                ejecutando = false;
            }
        }

        scanner.close();
    }

    public static int obtenerNumero(int limite){
        while (true){
            try{
                int numero = scanner.nextInt();
                if(numero <= limite && numero > 0){
                    return numero;
                }
            } catch (Exception e) {
                System.out.println("Introduzca un valor válido, por favor");
            }
        }
    }

    public static int obtenerOpcionMenu(){
        return obtenerNumero(12);
    }

    public static String corregirNombre(String nombre){
        Pattern pattern = Pattern.compile("[A-Z][a-z]{2,} [A-Z][a-z]{2,}(\\s[A-Z][a-z]{2,})*");
        Matcher matcher = pattern.matcher(nombre);
        if (matcher.find()) {
            return nombre;
        }
        return null;
    }


}
