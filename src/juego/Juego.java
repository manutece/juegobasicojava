package juego;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

import java.awt.Color;
import java.awt.Image;
import java.lang.Math;

public class Juego extends InterfaceJuego {

	private Entorno entorno;
	private Obstaculo[] obstaculos;
	private Mikasa mikasa;
	private Kyojin[] kyojines;
	private Proyectil proyectil;
	private Suero suero;
	private Image fondo;
	private boolean modoTitan; // esto debería ir en Mikasa
	private int numeroDeKyojinesMuertos; // numeroDeKyojinesMuertos
	private boolean ganaste;
	private boolean perdiste;
	private double timerDeDelayDeSuero; // timerDeDelayDeSuero
	private double tiempoDeAparicionDeKyojines;
	private double cooldownProyectil;

	public Juego() {
		// Inicializa todas las variables necesarias
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);
		this.fondo = Herramientas.cargarImagen("fondo.png");
		kyojines = new Kyojin[5];
		mikasa = new Mikasa(entorno.ancho() / 2, entorno.alto() / 2);
		obstaculos = new Obstaculo[6];
		numeroDeKyojinesMuertos = 0;
		tiempoDeAparicionDeKyojines = 400; // este numero determina que los kyojines van a aparecer cada 400 ticks
		cooldownProyectil = 0;
		timerDeDelayDeSuero = (Math.random() + 0.1) * (750 - 560 + 1) + 560; // numero aleatorio de ticks para delay de
																				// aparicion del suero
		// creador de coordenadas aleatorias para los obst�culos
		for (int i = 0; i < obstaculos.length; i++) {
			int minX = 0 + 90;
			int maxX = entorno.ancho() - 90;
			int minY = 0 + 90;
			int maxY = entorno.alto() - 90;
			double x = (Math.random() + 0.01) * (maxX - minX + 1) + minX;
			double y = (Math.random() + 0.01) * (maxY - minY + 1) + minY;
			while (x < entorno.ancho() / 2 + 90 && x > entorno.ancho() / 2 - 90 && y < entorno.alto() / 2 + 90
					&& y > entorno.alto() / 2 - 90) {
				x = (Math.random() + 0.01) * (maxX - minX + 1) + minX;
				y = (Math.random() + 0.01) * (maxY - minY + 1) + minY;
			}
			double selectorDeCasa = Math.random() * 10;
			String casaElegida = "Casa1";
			if (selectorDeCasa <= 3) {
				casaElegida = "Casa1";
			}
			if (selectorDeCasa <= 6 && selectorDeCasa > 3) {
				casaElegida = "Casa2";
			}
			if (selectorDeCasa > 6) {
				casaElegida = "Casa3";
			}

			obstaculos[i] = new Obstaculo(x, y, casaElegida);
		}

		// creador de coordenadas aleatorias para los kyojines, lejanas a mikasa
		for (int k = 0; k < kyojines.length; k++) {
			double xk = 0;
			int minY = 0 + 90;
			int maxY = entorno.alto() - 90;
			double lado = Math.random();
			if (lado <= 0.5) {
				xk = (Math.random() + 0.01) * (150 - 40 + 1) + 40;
			} else {
				xk = (Math.random() + 0.01) * ((entorno.ancho() - 40) - (entorno.ancho() - 150) + 1)
						+ (entorno.ancho() - 150);
			}
			double yk = (Math.random() + 0.1) * (maxY - minY + 1) + minY;

			kyojines[k] = new Kyojin(xk, yk);
		}

		this.entorno.iniciar();

	}

	public void tick() {

		// dibujo de mikasa y fondo y chequeo de colisiones de mikasa
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0, 2);
		mikasa.dibujar(entorno);
		mikasa.detenerEnfrenteDeObstaculos(this.obstaculos);

		// comportamiento kyojines
		for (int i = 0; i < kyojines.length; i++) {
			if (kyojines[i] != null) {
				kyojines[i].dibujar(entorno);
				kyojines[i].perseguirMikasa(mikasa);
				kyojines[i].detenerEnfrenteDeObstaculos(this.obstaculos);
				kyojines[i].evitarSuperposicionConKyojines(kyojines);
			}
		}

		// dibujo y colisiones entre obstaculos
		for (int i = 0; i < obstaculos.length; i++) {
			obstaculos[i].chocasteObstaculo(obstaculos); // ??? el nombre me da la idea de que esto devuelve un boolean
			obstaculos[i].dibujar(entorno);
		}
		// dibujo de suero
		if (this.suero != null) {

			suero.dibujar(entorno);
			// mikasa agarra suero
			if (mikasa.agarrasteSuero(suero)) {
				modoTitan = true;
				suero = null;
			}
		}
		// respawn suero, en su defecto, decrecimiento del cooldown
		if (this.suero == null) {
			if (timerDeDelayDeSuero < 1) {
				suero = new Suero((Math.random() + 0.1) * ((entorno.ancho() - 60) - 60),
						(Math.random() + 0.1 * ((entorno.alto() - 60) - 60)));
				timerDeDelayDeSuero = (Math.random() + 0.1) * (750 - 560 + 1) + 560;
			} else {
				timerDeDelayDeSuero--;
			}
		}

		// controles mikasa
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('l')) {
			mikasa.girarDerecha();
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('h')) {
			mikasa.girarIzquierda();
		}
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('k')) {
			mikasa.avanzar(entorno);
		}
		// creacion de proyectil para disparo
		if (entorno.estaPresionada(entorno.TECLA_ESPACIO) && proyectil == null && cooldownProyectil < 1) {
			proyectil = mikasa.disparar();
			cooldownProyectil = 200;
		}

		// cooldown del proyectil y cartel indicador de proyectil listo
		if (proyectil == null) {
			if (cooldownProyectil > 0) {
				cooldownProyectil--;
				entorno.cambiarFont("Arial", 18, Color.black);
				entorno.escribirTexto("Proyectil listo en: " + (cooldownProyectil), entorno.ancho() - 200,
						entorno.alto() - 30);
			} else {
				cooldownProyectil = 0;
				entorno.cambiarFont("Arial", 18, Color.black);
				entorno.escribirTexto("Proyectil listo", entorno.ancho() - 200, entorno.alto() - 30);
			}
		}
		// comportamiento del proyectil
		if (proyectil != null) {
			proyectil.dibujar(entorno);
			proyectil.avanzar();
			// colisi�n y borrado del proyectil
			if (proyectil.chocasteConEntorno(entorno) || proyectil.chocasteConObstaculo(obstaculos)) {
				proyectil = null;
			}
		}
		// borrado del proyectil si colisiona e incrementacion de contador de kyojines
		// muertos
		for (int i = 0; i < kyojines.length; i++) {
			if (proyectil != null) {
				if (proyectil.chocasteConKyojin(kyojines[i])) { // proyectil mata kyojines
					kyojines[i] = null;
					numeroDeKyojinesMuertos++;
					proyectil = null;
				}
			}
		}
		// mikasa mata enemigo si tiene suero y el contador se suma
		for (int i = 0; i < kyojines.length; i++) {
			if (mikasa.chocasteConKyojines(kyojines[i]) && modoTitan == true) { // muerte kyojin colison mikasa
				kyojines[i] = null;
				numeroDeKyojinesMuertos++;
				modoTitan = false;
			}
		}
		// contador de kyojines vivos
		int kVivos = 0;
		for (int k = 0; k < kyojines.length; k++) {
			if (kyojines[k] != null) {
				kVivos++;
			}
		}
		// spawneo de kyojines luego del cooldown
		for (int i = 0; i < kyojines.length; i++) {
			if (kyojines[i] == null) {
				if (tiempoDeAparicionDeKyojines < 1) {
					int maxY = entorno.alto() - 60;
					int minY = 0 + 60;
					kyojines[i] = new Kyojin((Math.random() + 0.1) * ((entorno.ancho() - 60) - 60),
							(Math.random() + 0.1) * (maxY - minY + 1) + minY);
					tiempoDeAparicionDeKyojines = 600;
				} else {
					tiempoDeAparicionDeKyojines--;
				}
			}
		}
		// mostrar kyojines asesinados
		entorno.cambiarFont("Arial", 18, Color.black);
		entorno.escribirTexto("Kyojines asesinados:" + (numeroDeKyojinesMuertos), entorno.ancho() - 200, 0 + 30);
		// ganar el juego
		if (kVivos == 0) {
			ganaste = true;
		}
		// perder el juego
		for (int i = 0; i < kyojines.length; i++) {
			if (mikasa.chocasteConKyojines(kyojines[i]) && modoTitan != true) { // muerte mikasa colision kyojin
				perdiste = true;
			}
		}
		if (ganaste) {
			entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() / 2, entorno.ancho(), entorno.alto(), 0,
					Color.BLACK);
			entorno.cambiarFont("Arial", 40, Color.green); // cuando ganas la partida
			entorno.escribirTexto("GANASTE", entorno.ancho() - 500, 200);
			entorno.cambiarFont("Arial", 20, Color.white);
			entorno.escribirTexto("Presiona ENTER para salir", entorno.ancho() - 520, 300);
			if (entorno.estaPresionada(entorno.TECLA_ENTER)) {
				System.exit(0);
			}
			return;
		}
		if (perdiste) {
			entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() / 2, entorno.ancho(), entorno.alto(), 0,
					Color.black);
			entorno.cambiarFont("Arial", 40, Color.red);
			entorno.escribirTexto("PERDISTE", entorno.ancho() - 500, 200);
			entorno.cambiarFont("Arial", 20, Color.white);
			entorno.escribirTexto("Presiona ENTER para salir", entorno.ancho() - 520, 300);
			if (entorno.estaPresionada(entorno.TECLA_ENTER)) {
				System.exit(0);
			}
			return;
		}
	}
	// cartel de victoria

	// cartel de derrota

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
