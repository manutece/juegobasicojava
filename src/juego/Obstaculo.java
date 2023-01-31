package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;

public class Obstaculo {

	private double x;
	private double y;
	private int alto;
	private int ancho;
	private String casaElegida;
	private Image casa1;
	private Image casa2;
	private Image casa3;

	// constructor de obstaculos
	public Obstaculo(double x, double y, String casaElegida) {
		this.x = x;
		this.y = y;
		this.alto = 70;
		this.ancho = 70;
		this.casaElegida = casaElegida;
		casa1 = Herramientas.cargarImagen("casa01.png");
		casa2 = Herramientas.cargarImagen("casa02.png");
		casa3 = Herramientas.cargarImagen("casa03.png");

	}

	// funcion de dibujado dependiendo de un valor random para que los dibujos
	// varien
	public void dibujar(Entorno e) {
		if (this.casaElegida.equals("Casa1")) {
			e.dibujarImagen(casa1, this.x, this.y, 0, 1.8);
		}
		if (this.casaElegida.equals("Casa2")) {
			e.dibujarImagen(casa2, this.x, this.y, 0, 1.8);
		}
		if (this.casaElegida.equals("Casa3")) {
			e.dibujarImagen(casa3, this.x, this.y, 0, 1.8);
		}

	}

	// colision de obstaculos con obstaculos para que no spawneen superpuestos
	public void chocasteObstaculo(Obstaculo[] obstaculos) {
		for (int i = 0; i < obstaculos.length; i++) {
			if (this.y + 40 > (obstaculos[i].getY() - obstaculos[i].getAlto() / 2)
					&& this.y - 40 < (obstaculos[i].getY() + obstaculos[i].getAlto() / 2)) {
				if (this.x - 40 < (obstaculos[i].getX() + obstaculos[i].getAncho() / 2)
						&& this.x > obstaculos[i].getX()) {
					this.x = (obstaculos[i].getX() + obstaculos[i].getAncho() / 2) + 40;
				}
				if (this.x + 40 > (obstaculos[i].getX() - obstaculos[i].getAncho() / 2)
						&& this.x < obstaculos[i].getX()) {
					this.x = (obstaculos[i].getX() - obstaculos[i].getAncho() / 2) - 40;
				}

			}
			if (this.x - 40 < (obstaculos[i].getX() + obstaculos[i].getAncho() / 2)
					&& this.x + 40 > (obstaculos[i].getX() - obstaculos[i].getAncho() / 2)) {
				if (this.y + 40 > (obstaculos[i].getY() - obstaculos[i].getAlto() / 2) - 5
						&& this.y < obstaculos[i].getY()) {
					this.y = (obstaculos[i].getY() - obstaculos[i].getAlto() / 2) - 5 - 40;
				}
				if (this.y - 40 < (obstaculos[i].getY() + obstaculos[i].getAlto() / 2) + 5
						&& this.y > obstaculos[i].getY()) {
					this.y = (obstaculos[i].getY() + obstaculos[i].getAlto() / 2) + 5 + 40;
				}
			}
		}
	}

	// getters de coordenadas y tama�o
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getAlto() {
		return alto;
	}

	public int getAncho() {
		return ancho;
	}

}
