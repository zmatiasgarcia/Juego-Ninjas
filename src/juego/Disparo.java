package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Disparo {

	// Variables de Instancia:
	private double x;
	private double y;
	private Image disparo;
	private char direccion;

	/** Constructor del Objeto : */

	Disparo(double x, double y, char direccion) {
		this.x = x;
		this.y = y;
		this.direccion = direccion;
		this.disparo = Herramientas.cargarImagen("rasengan.png");
	}

	/** Permite obtener el valor de x */

	public double getX() {
		return x;
	}

	/** Permite modificar el valor de x */
	
	public void setX(double x) {
		this.x = x;
	}

	/** Permite obtener el valor de y */

	public double getY() {
		return y;
	}
	
	/** Permite modificar el valor de y */
	
	public void setY(double y) {
		this.y = y;
	}

	/** Mueve el disparo en la dirrecion especificada */

	void moverse() { 
		switch (direccion) {
		case 'S':
			this.y -= 5;
			break;
		case 'B':
			this.y += 5;
			break;
		case 'I':
			this.x -= 5;
			break;
		case 'D':
			this.x += 5;
			break;
		}
	}
	
	boolean colision(Ninjas ninja) {
		if (this.x > ninja.getX() - ninja.getAncho()/2 &&
				this.x < ninja.getX() + ninja.getAncho()/2 &&
					this.y > ninja.getY() - ninja.getAltura()/2 &&
						this.y < ninja.getY() + ninja.getAltura()/2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	boolean colisionBordes() {
		if (this.x > 800 || this.x < 0 || this.y > 600 || this.y < 0) {
			return true;
		}
		return false;
	}
	/** Imprime en pantalla la imagen de un disparo */

	void disparar(Entorno e) {
		e.dibujarImagen(disparo, x, y, 0, 0.04);
		moverse();
	}
	

} // Cierre total del Programa
