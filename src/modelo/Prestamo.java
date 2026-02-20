package modelo;

import java.time.LocalDate;

/**
 * Representa un préstamo de un libro con fechas de inicio y vencimiento.
 */
public class Prestamo implements Identificable{

  private int id;
  private Libro libro;
  private LocalDate fechaPrestamo;
  private LocalDate fechaPrestamoVencimiento;

  /**
   * Crea un préstamo para el libro indicado con fecha actual.
   * 
   * @param libro libro que se presta.
   */
  public Prestamo(Libro libro) {
    this(libro, LocalDate.now());
  }

  /**
   * Crea un préstamo para el libro indicado para cualquier fecha
   * 
   * @param libro         libro que se presta
   * @param fechaRecogida fecha en la que se hará efectivo el préstamo
   */
  public Prestamo(Libro libro, LocalDate fechaRecogida) {
    this.id = (int) (Math.random() * 100000);
    this.libro = libro;
    this.fechaPrestamo = fechaRecogida;
    this.fechaPrestamoVencimiento = fechaPrestamo.plusDays(30);
  }



  /**
   * Obtiene el libro asociado al préstamo.
   * 
   * @return libro prestado.
   */
  public Libro getLibro() {
    return libro;
  }

  /**
   * Establece el libro asociado al préstamo.
   * 
   * @param libro nuevo libro prestado.
   */
  public void setLibro(Libro libro) {
    this.libro = libro;
  }

  /**
   * Obtiene la fecha en la que se realizó el préstamo.
   * 
   * @return fecha de préstamo.
   */
  public LocalDate getFechaPrestamo() {
    return fechaPrestamo;
  }

  /**
   * Establece la fecha del préstamo.
   * 
   * @param fechaPrestamo nueva fecha de préstamo.
   */
  public void setFechaPrestamo(LocalDate fechaPrestamo) {
    this.fechaPrestamo = fechaPrestamo;
  }

  @Override
  public boolean equals(Object o) {
    Prestamo prestamo = (Prestamo) o;
    if (prestamo.id == this.id) {
      return true;
    }

    return false;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void  setId() {
    this.id = (int) (Math.random() * 100000);
  }

  public int getId() {
    return id;
  }

}
