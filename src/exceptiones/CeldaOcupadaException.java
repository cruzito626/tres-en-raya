package exceptiones;

public class CeldaOcupadaException extends Exception {
  private static final long serialVersionUID = 1L;
  private static final String MENSAJE = "La celda: Fila[%s] Columna[%s] esta ocupada";

  public CeldaOcupadaException(int fila, int columna) {
    super(String.format(MENSAJE, fila, columna));
  }
}
