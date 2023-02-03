package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Shurikens {
	double x, y;
	int ancho,alto;
	Image shuriken;
	public Shurikens(double d, double e) {
		this.x = d;
		this.y = e;
		this.ancho = 20;
		this.alto = 20;
		this.shuriken = Herramientas.cargarImagen("shurikens.png");
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public int getAncho() {
		return ancho;
	}
	public int getAlto() {
		return alto;
	}
	
	void disparadorHorizontal(Entorno entorno) {
		entorno.dibujarImagen(shuriken, x, y, 0);
		moverseX();
	}
	void moverseX() {
		this.x +=1.1;
	}
	void disparadorVertical(Entorno entorno) {
		entorno.dibujarImagen(shuriken, x, y, 0);
		moverseY();
	}
	void moverseY() {
		this.y +=1.1;
	}
	
	boolean colisionBordes() {
		if (this.x > 800 || this.y > 600) {
			return true;
		}
		return false;
	}
	
}
