import exceptiones.CeldaOcupadaException;
import exceptiones.JuegoTerminadoException;
import exceptiones.PosicionNoExisteException;

public interface TresEnRaya {
  void jugar(Posicion posicion) throws CeldaOcupadaException, JuegoTerminadoException, PosicionNoExisteException;
  boolean enJuego() throws PosicionNoExisteException;
  String getResultado() throws PosicionNoExisteException;
  Ficha getJugadorEnTurno();
}
