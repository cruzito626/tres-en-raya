public class Posicion {
  private int x;
  private int y;

  public Posicion(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean estaRango(int ini, int fin) {
    return getX() >= ini && getX() < fin && getY() >= ini && getY() < fin;
  }
}
