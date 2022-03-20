import exceptiones.PosicionNoExisteException;

public abstract class Tablero {
  private int fila;
  private int columna;

  public Tablero(int filas, int columna) {
    this.fila = filas;
    this.columna = columna;
  }

  public int getFila() {
    return fila;
  }
  public int getColumna() {
    return fila;
  }

  abstract void setCelda(Posicion posicion, Ficha ficha) throws PosicionNoExisteException;
  abstract Ficha getCelda(Posicion posicion) throws PosicionNoExisteException;
}
