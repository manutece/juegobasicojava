package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Suero {

	private double x;
	private double y;
	private Image imagen;
	private double tamaño;

	// constructor de suero
	public Suero(double x, double y) {
		this.x = x;
		this.y = y;
		this.imagen = Herramientas.cargarImagen("suero.png");
		this.tamaño = 30;
	}

	// dibujar el suero
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.07);
	}

	// getters de posici�n
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getTamaño() {
		return tamaño;
	}

}
