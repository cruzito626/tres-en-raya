package exceptiones;

public class JuegoTerminadoException extends Exception {
  private static final long serialVersionUID = 1L;
  private static final String MENSAJE = "El juego ha Terminado!!";

  public JuegoTerminadoException() {
    super(MENSAJE);
  }
}
