package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Mikasa {

	private double x;
	private double y;
	
	private double velocidad;
	private double angulo;
	
	private double tamaño;
	private Image imagen;

	// constructor de mikasa
	public Mikasa(int x, int y) {
		this.x = x;
		this.y = y;
		this.velocidad = 3;
		this.angulo = 0;
		this.tamaño = 30;
		this.imagen = Herramientas.cargarImagen("mikasa .png");
	}

	// dibujar a mikasa
	public void dibujar(Entorno e) {
		e.dibujarImagen(this.imagen, this.x, this.y, this.angulo, 1);

	}

	// giro derecho de mikasa
	public void girarDerecha() {
		this.angulo = this.angulo + 0.05;
		if (this.angulo > Math.PI * 2) {
			this.angulo = this.angulo - Math.PI * 2;
		}
	}

	// giro izquierdo de mikasa
	public void girarIzquierda() {
		this.angulo = this.angulo - 0.05;
		if (this.angulo < 0) {
			this.angulo = this.angulo + Math.PI * 2;
		}
	}

	// mikasa avanza contemplando los limites
	public void avanzar(Entorno entorno) {
		this.x += Math.cos(this.angulo) * this.velocidad;
		this.y += Math.sin(this.angulo) * this.velocidad;
		if (this.x > entorno.ancho() - this.tamaño / 2) {
			this.x = entorno.ancho() - this.tamaño / 2;
		}
		if (this.x < 0 + this.tamaño / 2) {
			this.x = 0 + this.tamaño / 2;
		}
		if (this.y > entorno.alto() - this.tamaño / 2) {
			this.y = entorno.alto() - this.tamaño / 2;
		}
		if (this.y < 0 + this.tamaño / 2) {
			this.y = 0 + this.tamaño / 2;
		}
	}

	// colision con obstaculos
	public void detenerEnfrenteDeObstaculos(Obstaculo[] obstaculos) {
		for (int i = 0; i < obstaculos.length; i++) {
			if (this.y + this.tamaño / 2 > (obstaculos[i].getY() - obstaculos[i].getAlto() / 2)
					&& this.y - this.tamaño / 2 < (obstaculos[i].getY() + obstaculos[i].getAlto() / 2)) {
				if (this.x - this.tamaño / 2 < (obstaculos[i].getX() + obstaculos[i].getAncho() / 2)
						&& this.x > obstaculos[i].getX()) {
					this.x = (obstaculos[i].getX() + obstaculos[i].getAncho() / 2) + this.tamaño / 2;
				}
				if (this.x + this.tamaño / 2 > (obstaculos[i].getX() - obstaculos[i].getAncho() / 2)
						&& this.x < obstaculos[i].getX()) {
					this.x = (obstaculos[i].getX() - obstaculos[i].getAncho() / 2) - this.tamaño / 2;
				}

			}
			if (this.x - this.tamaño / 2 < (obstaculos[i].getX() + obstaculos[i].getAncho() / 2)
					&& this.x + this.tamaño / 2 > (obstaculos[i].getX() - obstaculos[i].getAncho() / 2)) {
				if (this.y + this.tamaño / 2 > (obstaculos[i].getY() - obstaculos[i].getAlto() / 2) - 5
						&& this.y < obstaculos[i].getY()) {
					this.y = (obstaculos[i].getY() - obstaculos[i].getAlto() / 2) - 5 - this.tamaño / 2;
				}
				if (this.y - this.tamaño / 2 < (obstaculos[i].getY() + obstaculos[i].getAlto() / 2) + 5
						&& this.y > obstaculos[i].getY()) {
					this.y = (obstaculos[i].getY() + obstaculos[i].getAlto() / 2) + 5 + this.tamaño / 2;
				}
			}
		}
	}

	// colisi�n con kyojines
	public boolean chocasteConKyojines(Kyojin kyojin) {
		if (kyojin != null) {
			if (this.y + this.tamaño / 25 > (kyojin.getY()
					- kyojin.getTamaño() / 2) && this.y - this.tamaño / 2 < (kyojin.getY() + kyojin.getTamaño() / 2)) {
				if (this.x - this.tamaño / 2 < (kyojin.getX() + kyojin.getTamaño() / 2) && this.x + this.tamaño / 2 > kyojin.getX()) {
					return true;
				}
				if (this.x + this.tamaño / 2 > (kyojin.getX() - kyojin.getTamaño() / 2) && this.x - this.tamaño / 2 < kyojin.getX()) {
					return true;
				}
			}
			if (this.x - this.tamaño / 2 < (kyojin.getX() + kyojin.getTamaño() / 2) && this.x + this.tamaño / 2 > (kyojin.getX() - kyojin.getTamaño() / 2)) {
				if (this.y + this.tamaño / 2 > (kyojin.getY() - kyojin.getTamaño() / 2) - 5 && this.y - this.tamaño / 2 < kyojin.getY()) {
					return true;
				}
				if (this.y - this.tamaño / 2 < (kyojin.getY() + kyojin.getTamaño() / 2) + 5 && this.y + this.tamaño / 2 > kyojin.getY()) {
					return true;
				}
			}
		}
		return false;

	}

	// mikasa agarra suero
	public boolean agarrasteSuero(Suero suero) {

			if (this.x + this.tamaño / 2 > (suero.getX() - suero.getTamaño() / 2) && this.x - this.tamaño / 2 < (suero.getX() + suero.getTamaño() / 2)) {
				if (this.y - this.tamaño / 2 < (suero.getY() + suero.getTamaño() / 2) && this.y + this.tamaño / 2 > suero.getY()) {
					return true;
				}
				if (this.y + this.tamaño / 2 > (suero.getY() - suero.getTamaño() / 2) && this.y - this.tamaño / 2 < suero.getY()) {
					return true;
				}
			}
			if (this.y - this.tamaño / 2 < (suero.getY() + suero.getTamaño() / 2) && this.y + this.tamaño / 2 > (suero.getY() - suero.getTamaño() / 2)) {
				if (this.x + this.tamaño / 2 > (suero.getX() - suero.getTamaño() / 2) && this.x - this.tamaño / 2 < suero.getX()) {
					return true;
				}
				if (this.x - this.tamaño / 2 < (suero.getX() + suero.getTamaño() / 2) && this.x + this.tamaño / 2 > suero.getX()) {
					return true;
				}
			}

		
		return false;
	}

	// getters de posicion
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAngulo() {
		return angulo;
	}

	public Proyectil disparar() {
		Proyectil proyectil = new Proyectil(this.x,this.y,this.angulo);
		return proyectil;
	}
}
