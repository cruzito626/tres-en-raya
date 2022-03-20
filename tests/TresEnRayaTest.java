import exceptiones.CeldaOcupadaException;
import exceptiones.JuegoTerminadoException;
import exceptiones.PosicionNoExisteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class TresEnRayaTest {

  private TresEnRaya tresEnRaya;

  @BeforeEach
  public void init() throws PosicionNoExisteException {
    tresEnRaya = new TresEnRayaImpl();
  }

  @Test
  public void jugar_EnCeldaVacia_Void()
      throws CeldaOcupadaException, JuegoTerminadoException, PosicionNoExisteException,
          NoSuchFieldException, IllegalAccessException {

    TableroTresEnRaya tableroTresEnRaya;
    Field attrTablero;

    attrTablero = TresEnRayaImpl.class.getDeclaredField("tablero");
    attrTablero.setAccessible(true);

    Posicion posicion = new Posicion(0, 0);
    Ficha jugador = Ficha.JUGADOR1;
    boolean estaVacio, estaMarcado;
    tableroTresEnRaya = (TableroTresEnRaya) attrTablero.get(tresEnRaya);

    estaVacio = tableroTresEnRaya.estaVacio(posicion);
    tresEnRaya.jugar(posicion);
    estaMarcado = tableroTresEnRaya.getCelda(posicion).equals(jugador);

    assertTrue(estaVacio && estaMarcado);
  }

  @Test
  public void jugar_EnCeldaMarcada_TrowCeldaOcupadaException()
      throws CeldaOcupadaException, JuegoTerminadoException, PosicionNoExisteException {
    Posicion posicion = new Posicion(0, 0);
    String mensaje =
        "La celda: Fila[%s] Columna[%s] esta ocupada".formatted(posicion.getX(), posicion.getY());

    tresEnRaya.jugar(posicion);

    assertThrows(CeldaOcupadaException.class, () -> tresEnRaya.jugar(posicion), mensaje);
  }

  @Test
  public void jugar_FueraDeRango_TrowPosicionNoExisteException() {
    String mensaje = "La celda no existe";

    assertThrows(
        PosicionNoExisteException.class, () -> tresEnRaya.jugar(new Posicion(4, 4)), mensaje);
  }

  @Test
  public void jugar_PosicionNula_TrowNullPointerException() {
    String mensaje = "La celda no existe";

    assertThrows(NullPointerException.class, () -> tresEnRaya.jugar(null), mensaje);
  }

  @Test
  public void jugar_EnPartidaTerminada_TrowJuegoTerminadoException()
      throws CeldaOcupadaException, JuegoTerminadoException, PosicionNoExisteException {

    tresEnRaya.jugar(new Posicion(0, 0));
    tresEnRaya.jugar(new Posicion(1, 0));
    tresEnRaya.jugar(new Posicion(0, 1));
    tresEnRaya.jugar(new Posicion(1, 1));
    tresEnRaya.jugar(new Posicion(0, 2));

    String mensaje = "La celda no existe";

    assertThrows(
        JuegoTerminadoException.class, () -> tresEnRaya.jugar(new Posicion(1, 2)), mensaje);
  }

  @Test
  public void getEstado_JuegoIniciado_EnProgreso() throws PosicionNoExisteException {
    assertTrue(tresEnRaya.enJuego());
  }

  @Test
  public void getEstado_JuegoEmpatado_Terminado()
      throws CeldaOcupadaException, JuegoTerminadoException, PosicionNoExisteException {

    tresEnRaya.jugar(new Posicion(0, 0));
    tresEnRaya.jugar(new Posicion(0, 1));
    tresEnRaya.jugar(new Posicion(1, 0));
    tresEnRaya.jugar(new Posicion(2, 0));
    tresEnRaya.jugar(new Posicion(0, 2));
    tresEnRaya.jugar(new Posicion(1, 1));
    tresEnRaya.jugar(new Posicion(2, 1));
    tresEnRaya.jugar(new Posicion(1, 2));
    tresEnRaya.jugar(new Posicion(2, 2));

    assertFalse(tresEnRaya.enJuego());
  }

  //    x|0|x
  //    x|0|0
  //    0|x|
  @Test
  public void getResultado_JuegoEmpatado_HanEmpatado()
      throws CeldaOcupadaException, JuegoTerminadoException, PosicionNoExisteException {

    tresEnRaya.jugar(new Posicion(0, 0));
    tresEnRaya.jugar(new Posicion(0, 1));
    tresEnRaya.jugar(new Posicion(1, 0));
    tresEnRaya.jugar(new Posicion(2, 0));
    tresEnRaya.jugar(new Posicion(0, 2));
    tresEnRaya.jugar(new Posicion(1, 1));
    tresEnRaya.jugar(new Posicion(2, 1));
    tresEnRaya.jugar(new Posicion(1, 2));
    tresEnRaya.jugar(new Posicion(2, 2));

    assertEquals("Han empatado!", tresEnRaya.getResultado());
  }

  @Test
  public void getResultado_EnPartidaTerminada_HayGanadorXPerdedor0()
      throws CeldaOcupadaException, JuegoTerminadoException, PosicionNoExisteException {
    String mensaje = "Felicidades JUGADOR1 has ganado!\nLo sentimos JUGADOR2 has perdido!";
    tresEnRaya.jugar(new Posicion(0, 0));
    tresEnRaya.jugar(new Posicion(1, 0));
    tresEnRaya.jugar(new Posicion(0, 1));
    tresEnRaya.jugar(new Posicion(1, 1));
    tresEnRaya.jugar(new Posicion(0, 2));

    assertEquals(mensaje, tresEnRaya.getResultado());
  }

  //    0|X|X
  //    0|X|X
  //    0|0|
  @Test
  public void getResultado_EnPartidaTerminada_HayGanador0PerdedorX()
      throws CeldaOcupadaException, JuegoTerminadoException, PosicionNoExisteException {

    String mensaje = "Felicidades JUGADOR2 has ganado!\nLo sentimos JUGADOR1 has perdido!";

    tresEnRaya.jugar(new Posicion(0, 1));
    tresEnRaya.jugar(new Posicion(0, 0));

    tresEnRaya.jugar(new Posicion(1, 1));
    tresEnRaya.jugar(new Posicion(2, 1));

    tresEnRaya.jugar(new Posicion(1, 2));
    tresEnRaya.jugar(new Posicion(1, 0));

    tresEnRaya.jugar(new Posicion(0, 2));
    tresEnRaya.jugar(new Posicion(2, 0));

    assertEquals(mensaje, tresEnRaya.getResultado());
  }
}
