public enum Ficha {
  JUGADOR1("X"),
  JUGADOR2("0"),
  VACIO("");

  private String icono;

  Ficha(String icono) {
    this.icono = icono;
  }

  public String getIcono() {
    return icono;
  }

  public boolean esVacio() {
    return this.equals(VACIO);
  }
}
