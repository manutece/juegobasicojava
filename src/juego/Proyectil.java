package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Proyectil {

	private double x;
	private double y;
	private double angulo;
	private double velocidad;
	private Image imagen;

	// constructor del proyectil
	public Proyectil(double x, double y, double angulo) {
		this.x = x;
		this.y = y;
		this.angulo = angulo;
		this.velocidad = 5;
		this.imagen = Herramientas.cargarImagen("proyectil.png");
	}

	// dibujar proyectil
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(this.imagen, this.x, this.y, this.angulo + Math.PI / 3, 0.1);
	}

	// avanza el proyectil
	public void avanzar() {
		this.x += Math.cos(this.angulo) * this.velocidad;
		this.y += Math.sin(this.angulo) * this.velocidad;
	}

	// colisi�n con obst�culos
	public boolean chocasteConObstaculo(Obstaculo[] obstaculos) {
		for (int i = 0; i < obstaculos.length; i++) {
			if (this.y > (obstaculos[i].getY() - obstaculos[i].getAlto() / 2)
					&& this.y < (obstaculos[i].getY() + obstaculos[i].getAlto() / 2)) {
				if (this.x < (obstaculos[i].getX() + obstaculos[i].getAncho() / 2) && this.x > obstaculos[i].getX()) {
					return true;
				}
				if (this.x > (obstaculos[i].getX() - obstaculos[i].getAncho() / 2) && this.x < obstaculos[i].getX()) {
					return true;
				}

			}
			if (this.x < (obstaculos[i].getX() + obstaculos[i].getAncho() / 2)
					&& this.x > (obstaculos[i].getX() - obstaculos[i].getAncho() / 2)) {
				if (this.y > (obstaculos[i].getY() - obstaculos[i].getAlto() / 2) - 5
						&& this.y < obstaculos[i].getY()) {
					return true;
				}
				if (this.y < (obstaculos[i].getY() + obstaculos[i].getAlto() / 2) + 5
						&& this.y > obstaculos[i].getY()) {
					return true;
				}
			}
		}
		return false;
	}

	// colisi�n con entorno
	public boolean chocasteConEntorno(Entorno entorno) {
		if (this.x > entorno.ancho()) {
			return true;
		}
		if (this.x < 0) {
			return true;
		}
		if (this.y > entorno.alto()) {
			return true;
		}
		if (this.y < 0) {
			return true;
		}
		return false;
	}

	// colisi�n con kyojines
	public boolean chocasteConKyojin(Kyojin kyojin) {
		if (kyojin != null) {
			if (this.y > (kyojin.getY() - kyojin.getTamaño() / 2)
					&& this.y < (kyojin.getY() + kyojin.getTamaño() / 2)) {
				if (this.x < (kyojin.getX() + kyojin.getTamaño() / 2) && this.x > kyojin.getX()) {
					return true;
				}
				if (this.x > (kyojin.getX() - kyojin.getTamaño() / 2) && this.x < kyojin.getX()) {
					return true;
				}
			}
			if (this.x < (kyojin.getX() + kyojin.getTamaño() / 2)
					&& this.x > (kyojin.getX() - kyojin.getTamaño() / 2)) {
				if (this.y > (kyojin.getY() - kyojin.getTamaño() / 2) - 5 && this.y < kyojin.getY()) {
					return true;
				}
				if (this.y < (kyojin.getY() + kyojin.getTamaño() / 2) + 5 && this.y > kyojin.getY()) {
					return true;
				}
			}
		}
		return false;

	}
}
