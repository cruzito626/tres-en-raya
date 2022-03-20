import exceptiones.CeldaOcupadaException;
import exceptiones.JuegoTerminadoException;
import exceptiones.PosicionNoExisteException;

public class TresEnRayaImpl implements TresEnRaya {
  private final Ficha JUGADOR1 = Ficha.JUGADOR1;
  private final Ficha JUGADOR2 = Ficha.JUGADOR2;
  private TableroTresEnRaya tablero;
  private Ficha jugadorEnTurno;

  public TresEnRayaImpl() throws PosicionNoExisteException {
    setTablero(new TableroTresEnRaya());
    setJugadorEnTurno(JUGADOR1);
  }

  @Override
  public void jugar(Posicion posicion) throws CeldaOcupadaException, JuegoTerminadoException, PosicionNoExisteException {
    validarEstado();
    validarCelda(posicion);
    marcarCelda(posicion);
    actualizarJuego();
  }

  private void cambiarTurno() {
    setJugadorEnTurno(jugadorEnTurno.equals(JUGADOR1) ? JUGADOR2 : JUGADOR1);
  }

  @Override
  public boolean enJuego() throws PosicionNoExisteException {
    return !(hayTresEnRaya() || tablero.estaLleno());
  }

  @Override
  public String getResultado() throws PosicionNoExisteException {
    if (hayTresEnRaya()) {
      return switch (jugadorEnTurno) {
        case JUGADOR1 -> Resultado.fomat(JUGADOR1, JUGADOR2);
        case JUGADOR2 -> Resultado.fomat(JUGADOR2, JUGADOR1);
        case VACIO -> null;
      };
    }

    if (tablero.estaLleno()) {
      return Resultado.MENSAJE_EMPATE;
    }
    return null;
  }

  @Override
  public Ficha getJugadorEnTurno() {
    return jugadorEnTurno;
  }

  @Override
  public String toString(){
    return tablero.toString();
  }

  private void actualizarJuego() throws PosicionNoExisteException {
    if (enJuego()) {
      cambiarTurno();
    }
  }

  private boolean iguales(Ficha[] linea) {
    Ficha pivote;
    pivote = linea[0];

    for (Ficha elemeto : linea) {
      if (!elemeto.equals(pivote) || elemeto.equals(Ficha.VACIO)) {
        return false;
      }
    }

    return true;
  }

  private boolean hayTresEnRaya() {
    Ficha[] fila, columna, diagonal, diagonalInvertida;

    for (int indice = 0; indice < tablero.getFila(); indice++) {
      fila = tablero.getFila(indice);
      columna = tablero.getColumna(indice);
      if (iguales(fila) || iguales(columna)) {
        return true;
      }
    }

    diagonal = tablero.getDiagonal();
    diagonalInvertida = tablero.getDiagonalInvertida();

    return iguales(diagonal) || iguales(diagonalInvertida);
  }

  private void validarEstado() throws JuegoTerminadoException, PosicionNoExisteException {
    if (!enJuego()) {
      throw new JuegoTerminadoException();
    }
  }

  private void validarCelda(Posicion posicion) throws CeldaOcupadaException, PosicionNoExisteException {
    if (!tablero.estaVacio(posicion)) {
      throw new CeldaOcupadaException(posicion.getX(), posicion.getY());
    }
  }

  private void marcarCelda(Posicion posicion) throws PosicionNoExisteException {
    tablero.setCelda(posicion, jugadorEnTurno);
  }

  private void setJugadorEnTurno(Ficha turnoJugador) {
    this.jugadorEnTurno = turnoJugador;
  }

  private void setTablero(TableroTresEnRaya nuevoTableroTresEnRaya) {
    this.tablero = nuevoTableroTresEnRaya;
  }
}
