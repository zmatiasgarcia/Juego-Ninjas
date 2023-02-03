package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Casas {
	private int x, y;
	private int ancho, alto;
	private Image flores;

	public Casas(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.flores = Herramientas.cargarImagen("flores.png");
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getAncho() {
		return ancho;
	}
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	public boolean colisiona(Sakura sakura) {
		if (sakura.getX() < this.x + this.ancho/2
				&& sakura.getX() > this.x - this.ancho/2
				&& sakura.getY() < this.y + this.alto/2
					&& sakura.getY() > this.y - this.alto/2) {
			return true;
		}else {
			return false;
		}
	}
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(flores, this.x, this.y, 0);
	}
}
