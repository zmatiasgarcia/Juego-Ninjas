package juego;

public class Nivel {

	private String nombre;
	private int nivel;
	private int cantNinjas;
	private double velocidadNinjas;
	private boolean disparoNinjas;
	private boolean clavosNinjas;
	private int cantEntregas;
	private int cantPremios;
	private int tiempo;
	
	public  Nivel( int nivel)  {
		this.nivel = nivel;
		this.nombre = "Nivel: " + this.nivel;
		this.cantNinjas = 4;
		this.velocidadNinjas = 0.5;
		this.disparoNinjas = false;
		this.clavosNinjas = false;
		this.cantEntregas = 3;
		this.cantPremios = 3;
		this.tiempo = 180;
		
	}
	
	public void subirNivel() {
		this.nivel ++;
		this.nombre = "Nivel: " + this.nivel;
		this.cantNinjas += 2;
		this.velocidadNinjas += 0.25;
		
		if (this.clavosNinjas == true) {
			this.disparoNinjas = true;
		}
		
		this.clavosNinjas = true;
		this.cantEntregas += 1;
		this.cantPremios += 1;
		this.tiempo += 60;
	}
	
	public int getNivel() {
		return nivel;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public int getCantNinjas() {
		return cantNinjas;
	}
	
	public double getVelocidadNinjas() {
		return velocidadNinjas;
	}
	
	public boolean getClavosNinjas() {
		return clavosNinjas;
	}
	
	public boolean getDisparoNinjas() {
		return disparoNinjas;
	}
	
	public int getCantEntregas() {
		return cantEntregas;
	}
	
	public int getCantPremios() {
		return cantPremios;
	}
	
	public int getTiempo() {
		return tiempo;
	}
}

