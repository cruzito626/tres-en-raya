public class Resultado {
  static final String MENSAJE_GANADOR_PERDEDOR = "Felicidades %s has ganado!\nLo sentimos %s has perdido!";
  static final String MENSAJE_EMPATE = "Han empatado!";

  public static String fomat(Ficha ganador, Ficha perdedor) {
    return Resultado.MENSAJE_GANADOR_PERDEDOR.formatted(ganador, perdedor);
  }
}
