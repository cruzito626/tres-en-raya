import exceptiones.PosicionNoExisteException;

import static java.util.Objects.requireNonNull;

public class TableroTresEnRaya extends Tablero {
  private Ficha[][] CELDAS = new Ficha[getFila()][getColumna()];
  private enum Direcciones {VERTICAL, HORIZONTAL, DIAGONAL, DIAGONAL_INVERTIDA}

  public TableroTresEnRaya() throws PosicionNoExisteException {
    super(3,3);
    init();
  }

  private void init() throws PosicionNoExisteException {
    for (int x = 0; x < getFila(); x++) {
      for (int y = 0; y < getColumna(); y++) {
        setCelda(new Posicion(x,y), Ficha.VACIO);
      }
    }
  }

  public void setCelda(Posicion posicion, Ficha ficha) throws PosicionNoExisteException {
    validaPosicion(posicion);
    validarFicha(ficha);

    int x = posicion.getX();
    int y = posicion.getY();

    CELDAS[x][y] = ficha;
  }

  public Ficha getCelda(Posicion posicion) throws PosicionNoExisteException {
    validaPosicion(posicion);
    int x = posicion.getX();
    int y = posicion.getY();

    return CELDAS[x][y];
  }

  public Ficha[] getFila(int indice) {
    return getLinea(Direcciones.HORIZONTAL, indice);
  }

  public Ficha[] getColumna(int indice) {
    return getLinea(Direcciones.VERTICAL, indice);
  }

  public Ficha[] getDiagonal() {
    return getLinea(Direcciones.DIAGONAL,null);
  }

  public Ficha[] getDiagonalInvertida(){
    return getLinea(Direcciones.DIAGONAL_INVERTIDA, null);
  }

  public boolean estaLleno() throws PosicionNoExisteException {
    for(int x = 0; x < getFila(); x++) {
      for(int y = 0; y < getColumna(); y++) {
        if (estaVacio(new Posicion(x, y))) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean estaVacio(Posicion posicion) throws PosicionNoExisteException {
     return getCelda(posicion).esVacio();
  }

  private Ficha[] getLinea(Direcciones direccion, Integer indice) {
    Ficha[] linea = new Ficha[getFila()];

    for(int pos = 0; pos < getFila(); pos++) {
      linea[pos] = switch (direccion) {
        case VERTICAL -> CELDAS[pos][indice];
        case HORIZONTAL -> CELDAS[indice][pos];
        case DIAGONAL -> CELDAS[pos][pos];
        case DIAGONAL_INVERTIDA -> CELDAS[pos][(getFila()-1)-pos];
      };
    }

    return linea;
  }

  public String toString() {
    StringBuilder str = new StringBuilder();

    for(int x = 0; x < getFila(); x++) {
      for(int y = 0; y < getFila(); y++) {
        try {
          str.append("[%s]".formatted(getCelda(new Posicion(x, y)).getIcono()));
        } catch (PosicionNoExisteException e) {
          e.printStackTrace();
        }
      }
      str.append("\n");
    }

    return str.toString();
  }

  private void validaPosicion(Posicion posicion) throws PosicionNoExisteException {
    requireNonNull(posicion, "El parametro posicion no puede ser null");

    if (!posicion.estaRango(0, getFila())) {
      throw new PosicionNoExisteException();
    }
  }

  private void validarFicha(Ficha ficha) {
    requireNonNull(ficha, "El parametro marca no puede ser null");
  }

}
