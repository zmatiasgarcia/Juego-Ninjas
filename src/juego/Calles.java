package juego;

public class Calles {

	private int x,y;
	private int largo, ancho;

	
	public Calles(int x, int y, int largo, int ancho) {
		this.x = x;
		this.y = y;
		this.largo = largo;
		this.ancho = ancho;
	}
	//retorna el valor x de la calle	
	public int getX() {
		return this.x;
	}
	//retorna el valor y de la calle
	public int getY() {
		return this.y;
	}
	
	public int getLargo(){
		return this.largo;
	}
	
	public int getAncho() {
		return this.ancho;
	}
}
