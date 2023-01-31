package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Kyojin {

	private double x;
	private double y;
	private double velocidad;
	private Image imagen;
	private double tamaño;

	// constructor de kyojin
	public Kyojin(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 0.8;
		this.tamaño = 30;
		this.imagen = Herramientas.cargarImagen("kyojin.png");
	}

	// dibujar kyojines
	public void dibujar(Entorno e) {
		e.dibujarImagen(this.imagen, this.x, this.y, 0, 0.2);
	}

	// perseguir a mikasa
	public void perseguirMikasa(Mikasa mikasa) {
		if (this.x < mikasa.getX()) {
			this.x = this.x + this.velocidad;
		}
		if (this.x > mikasa.getX()) {
			this.x = this.x - this.velocidad;
		}
		if (this.y < mikasa.getY()) {
			this.y = this.y + this.velocidad;
		}
		if (this.y > mikasa.getY()) {
			this.y = this.y - this.velocidad;
		}

	}

	// colision con el entorno
	public void limiteConElEntorno(Entorno e) {
		if (this.x < 0) {
			this.x = 0;
		}
		if (this.x > e.ancho()) {
			this.x = e.ancho();
		}
		if (this.y < 0) {
			this.y = 0;
		}
		if (this.y > e.ancho()) {
			this.y = e.ancho();
		}
	}

	// colision con los kyojines
	public void evitarSuperposicionConKyojines(Kyojin[] kyojines) {
		for (int i = 0; i < kyojines.length; i++) {
			if (kyojines[i] != null) {
				if (this.y + this.tamaño / 2 > (kyojines[i].getY() - kyojines[i].getTamaño() / 2)
						&& this.y - 15 < (kyojines[i].getY() + kyojines[i].getTamaño() / 2)) {
					if (this.x - this.tamaño / 2 < (kyojines[i].getX() + kyojines[i].getTamaño() / 2)
							&& this.x > kyojines[i].getX()) {
						this.x = (kyojines[i].getX() + kyojines[i].getTamaño() / 2) + this.tamaño / 2;
					}
					if (this.x + this.tamaño / 2 > (kyojines[i].getX() - kyojines[i].getTamaño() / 2)
							&& this.x < kyojines[i].getX()) {
						this.x = (kyojines[i].getX() - kyojines[i].getTamaño() / 2) - this.tamaño / 2;
					}
				}
				if (this.x - this.tamaño / 2 < (kyojines[i].getX() + kyojines[i].getTamaño() / 2)
						&& this.x + this.tamaño / 2 > (kyojines[i].getX() - kyojines[i].getTamaño() / 2)) {
					if (this.y + this.tamaño / 2 > (kyojines[i].getY() - kyojines[i].getTamaño() / 2) - 5
							&& this.y < kyojines[i].getY()) {
						this.y = (kyojines[i].getY() - kyojines[i].getTamaño() / 2) - 5 - this.tamaño / 2;
					}
					if (this.y - this.tamaño / 2 < (kyojines[i].getY() + kyojines[i].getTamaño() / 2) + 5
							&& this.y > kyojines[i].getY()) {
						this.y = (kyojines[i].getY() + kyojines[i].getTamaño() / 2) + 5 + this.tamaño / 2;
					}
				}
			}
		}
	}

	// colision con los obstaculos
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
