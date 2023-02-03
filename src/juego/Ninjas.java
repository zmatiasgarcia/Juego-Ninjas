package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Ninjas {
	private double x,y;
	private Image img;
	private double velocidadH;
	private double velocidadY;
	private int altura, ancho;
	
	public Ninjas(double x, double y, double velocidad ) {
		this.x = x;
		this.y = y;
		this.velocidadH = velocidad;
		this.velocidadY = velocidad;
		this.altura = 29;
		this.ancho = 23;
		this.img = Herramientas.cargarImagen("ninja.PNG");
	}
	
	
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	
	public int getAncho() {
		return ancho;
	}
	
	public int getAltura() {
		return altura;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void setVelocidad(double velocidad) {
		this.velocidadH = velocidad;
		this.velocidadY = velocidad;
	}

	void moverseHorizontal() {
			this.x = this.x + this.velocidadH;
			if (x > 795) {
				this.x = 5;
			}else if (x < 5) {
				this.x = 795;
			}
	}
	
	void moverseVertical() {
		this.y = this.y + this.velocidadY;
		if (y > 595) {
			this.y = 5;
		} else if (y < 5) {
			this.y = 595;
		}
	}
	
	void dibujarse(Entorno e) {
		e.dibujarImagen(img, x, y, 0);
	}
	
	public static void actualizarVidaNinjas(Ninjas []ninjaHorizontal,Ninjas []ninjaVertical,int []vidaNinjaH, int []vidaNinjaV, int countTicks) {
		
		for (int i = 0; i < vidaNinjaH.length; i++) {
			if (ninjaHorizontal[i]!=null) {
				vidaNinjaH[i]=countTicks/40;
			}
		}
		
		for (int i = 0; i < vidaNinjaV.length; i++) {
			if (ninjaVertical[i]!=null) {
				vidaNinjaV[i]=countTicks/40;
			}
		}
	}
	
	public static void agregarNinjas(Ninjas []ninjaHorizontal,Ninjas []ninjaVertical,int []vidaNinjaH, int []vidaNinjaV, int countTicks, double velocidad) {
		int tiempoEspera = 30;
		int tiempoJuego = countTicks/40;
		int y = 89;
		int x = 5;
		for (int i = 0; i < ninjaHorizontal.length; i++) {
			
			if (ninjaHorizontal[i] == null && tiempoJuego > vidaNinjaH[i] + tiempoEspera ) {
				ninjaHorizontal[i] = new Ninjas(x, y,velocidad);
			}
			y += 144;
		}
		
		x = 120;
		y = 5;
		for (int i = 0; i < ninjaVertical.length; i++) {
			if (ninjaVertical[i] == null && tiempoJuego > vidaNinjaV[i] + tiempoEspera) {
				ninjaVertical[i] = new Ninjas(x, y, velocidad);
			}
			x += 190;
		}
	}
	
	boolean buscadorHorizontal(Sakura sakura) {
		if (sakura.getX() - this.x<= 100 && sakura.getX() - this.x > 0 &&
				sakura.getY() < this.y + this.altura/2 &&
					sakura.getY() > this.y - this.altura/2) {
			return true;
				}
		return false;
	}
	
	boolean buscadorVertical(Sakura sakura) {
		if (sakura.getY() - this.y <= 100 && sakura.getY() - this.y > 0 && 
				sakura.getX() < this.x + this.ancho/2 &&
					sakura.getX() > this.x - this.ancho/2) {
			return true;
		}
		return false;
	}
}
