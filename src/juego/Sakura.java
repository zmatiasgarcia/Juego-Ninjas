package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Sakura {
	public int x;
	public int y;
	public int cantSalto;
	public Image Sakura;
	public char ultimaDireccion;
	
	
	Sakura(int x, int y) {
		this.x = x;
		this.y = y;
		this.cantSalto = 1;
		this.ultimaDireccion = 'S';
		this.Sakura = Herramientas.cargarImagen("Sakura.png");
	}
	
	public double getX() {
		return x;
	}	

	/** Permite obtener el valor de y */
	public double getY() {
		return y;
	}
	
	public int getCantSalto() {
		return cantSalto;
	}
	
	public char getUltimaDireccion() {
		return ultimaDireccion;
	}
	
	void salto() {
		this.y -= cantSalto;
		this.ultimaDireccion = 'S';
	}
	
	/** Mueve a sakura hacia la derecha, disminuyendo la x */
	
	void saltarDerecha() {
		this.x += cantSalto;
		this.ultimaDireccion = 'D';
	}
	
	/** Mueve a sakura hacia la izquierda, incrementando la x */
	
	void saltarIzquierda() {
		this.x -= cantSalto;
		this.ultimaDireccion = 'I';
	}

	/** Decrece la posición de sakura constantemente */

	void caer() {
		this.y += 0;
	}
	
	void saltarAbajo() {
		this.y += cantSalto;
		this.ultimaDireccion = 'B';
	}
	
	void rebotarBordes() {
		if(this.x > 795 ) {
			this.x -= 10;
		}
		if(this.y > 595 ){
			this.y -= 10;
		}
		if(this.x < 5) {
			this.x += +10;
		}
		if(this.y < 5){
			this.y += 10;
		}
	}
	
	boolean colisionNinja (Ninjas ninja) {
		if (this.x < ninja.getX() + ninja.getAncho()/2
			&& this.x > ninja.getX() - ninja.getAncho()/2
				&& this.y < ninja.getY() + ninja.getAltura()/2
					&& this.y > ninja.getY() - ninja.getAltura()/2) {
			return true;
		}else {
			return false;
		}
	}
	
	boolean colisionShuriken (Shurikens shuriken) {
		if (this.x < shuriken.getX() + shuriken.getAncho()/2
				&& this.x > shuriken.getX() - shuriken.getAncho()/2
					&& this.y < shuriken.getY() + shuriken.getAlto()/2
						&& this.y > shuriken.getY() - shuriken.getAlto()/2) {
				return true;
			}else {
				return false;
			} 
	}
	
	boolean colisionClavos (Clavos clavo) {
		if (this.x < clavo.getX() + clavo.getAncho()/2
				&& this.x > clavo.getX() - clavo.getAncho()/2
					&& this.y < clavo.getY() + clavo.getAlto()/2
						&& this.y > clavo.getY() - clavo.getAlto()/2) {
				return true;
			}else {
				return false;
			}
	}

	/** Dibuja en Pantalla la imagen  */

	void imprimir(Entorno e) {
		e.dibujarImagen(Sakura, x, y, 0, 0.50);
	}
	
	public static boolean moverSakura(char direccion, Sakura Sakura, Calles []Lcalles) {
		
		for (int i = 0; i < Lcalles.length; i++) {
			int posX = Lcalles[i].getX();
			int posY = 	Lcalles[i].getY();
			int ancho = Lcalles[i].getAncho();
			int largo = Lcalles[i].getLargo();
			int saltoSakura = Sakura.getCantSalto();
			int margen = 8;
			
			switch (direccion) {
			case 'S':
				if (Sakura.getY() - saltoSakura >= posY  && Sakura.getY() - saltoSakura <= posY+largo && posY==0 
					&& Sakura.getX() >= posX + ancho/2 - margen && Sakura.getX() <= posX + ancho/2 + margen) {
					
					return true;
				}
				break;
			case 'B':
				if (Sakura.getY() + saltoSakura >= posY  && Sakura.getY() + saltoSakura <= posY+largo && posY==0 
						&& Sakura.getX() >= posX + ancho/2 - margen && Sakura.getX() <= posX + ancho/2 + margen) {
					
					return true;
				}
				break;
			case 'I':
				if (Sakura.getX() - saltoSakura >= posX  && Sakura.getX() - saltoSakura <= posX+largo && posX==0 
					&& Sakura.getY() >= posY + ancho/2 - margen && Sakura.getY() <= posY + ancho/2 + margen ) {
					
					return true;
				}
				break;
			case 'D':
				if (Sakura.getX() + saltoSakura >= posX  && Sakura.getX() + saltoSakura <= posX+largo && posX==0 
					&& Sakura.getY() >= posY + ancho/2 - margen && Sakura.getY() <= posY + ancho/2 + margen) {
					
					return true;
				}
				break;
			}
		}
		return false;
	}

} // Cierre total del Programa


