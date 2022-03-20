package exceptiones;

public class PosicionNoExisteException extends Exception  {
  private static final long serialVersionUID = 1L;
  private static final String MENSAJE = "La celda no existe!";

  public PosicionNoExisteException() {
    super(MENSAJE);
  }
}
