package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Clavos {
	double x, y;
	int ancho, alto;
	Image clavos;
	
	public Clavos(double d, double e){
		this.x = d;
		this.y = e;
		this.ancho = 20;
		this.alto = 20;
		this.clavos = Herramientas.cargarImagen("clavos.png");
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
	
	void dibujar(Entorno entorno) {
		entorno.dibujarImagen(clavos, x, y, 0);
	}
}
