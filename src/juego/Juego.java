package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import javax.sound.sampled.Clip;

import java.awt.Font;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;


public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	
	private Entorno entorno;
	
	// Variables:
	Casas casas [];
	int countTicks;
	Image calles;
	Image gameover;
	Image subirNivel;
	Image juegoTerminado;
	Sakura Sakura;
	Disparo disparo;
	Calles [] Lcalles;
	int NinjasMuertos;
	Ninjas [] ninjaHorizontal;
	Ninjas [] ninjaVertical;
	int [] vidaNinjaH;
	int [] vidaNinjaV;
	Clip musica;
	Clip poder;
	Clip venta;
	Clip perder;
	Clip ganar;
	Clip musicaPerder;
	Clip musicaGanar;
	Nivel nivel;
	int CantNiveles;
	Image flores;
	int puntaje;
	int entregas;
	int numeroCasaGanadora;
	Random r;
	Shurikens [] shurikensH;
	Shurikens [] shurikensV;
	Clavos [] clavosH;
	Clavos [] clavosV;
	
	
	
	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Sakura Ikebana Delivery - Grupo N°5 - Garcia - Nissero - Portillo - V0.01", 1000, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.inicializarTodo();
		

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el mÃ©todo tick() serÃ¡ ejecutado en cada instante y 
	 * por lo tanto es el mÃ©todo mÃ¡s importante de esta clase. AquÃ­ se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		// ...
		if (colisionNinja() || colisionShuriken() || colisionClavos()) {
			
			
			//Detiene el juego por que se perdió
			this.musica.stop();
			gameOver();
			
			restartGame();
			
		} else if (this.entregas >= this.nivel.getCantEntregas()) {
			if (this.CantNiveles == this.nivel.getNivel()) {
				juegoTerminado();
				restartGame();
			}
			else {
				subirNivel();
				inicializarNivel();
			}
		}
		else {
		this.entorno.dibujarImagen(calles, 400, 300, 0);
		this.Sakura.imprimir(entorno);
//		String textoPosX = "X=" + this.Sakura.getX();
//		String textoPosY = "Y=" + this.Sakura.getY();
		String NinjasEliminados = "Ninjas Eliminados: " + this.NinjasMuertos;
		String Entregas = "Entregas:  " + this.entregas + "/" + this.nivel.getCantEntregas();
		String Puntaje = "Puntaje: " + this.puntaje;
		this.musica.loop(10);
		
		Color color= new Color(255,255,255);
		//Aumenta el tiempo dentro del juego
		this.countTicks++;
		
		this.entorno.cambiarFont(Font.SANS_SERIF, 20, color);
		
//		this.entorno.escribirTexto(textoPosX, 800, 20);
//		this.entorno.escribirTexto(textoPosY, 800, 40);
		this.entorno.escribirTexto(this.nivel.getNombre(),800,60);
		this.entorno.escribirTexto(NinjasEliminados, 800, 80);
		this.entorno.escribirTexto("Tiempo:" + this.countTicks/40, 800, 100);
		this.entorno.escribirTexto(Entregas, 800, 120);
		this.entorno.escribirTexto(Puntaje, 800, 140);
		
		
		this.Sakura.rebotarBordes();
		saltoSakura();
		dibujadorDeNinjas();
		if(this.disparo !=null) {
			this.disparo.disparar(entorno);
		}
		
		disparoDeSakura();
		Shuriken();
		shurikensDisparador();
		clavos();
		dibujadorClavos();
		Ninjas.agregarNinjas(this.ninjaHorizontal,this.ninjaVertical,this.vidaNinjaH,this.vidaNinjaV,this.countTicks,this.nivel.getVelocidadNinjas());
		Ninjas.actualizarVidaNinjas(this.ninjaHorizontal,this.ninjaVertical,this.vidaNinjaH,this.vidaNinjaV,this.countTicks);
		colisionCasa();
		
		
		}
	}
	
	void inicializarTodo() {
		this.gameover = Herramientas.cargarImagen("gameover.png");
		this.subirNivel = Herramientas.cargarImagen("trofeo-plata.png");
		this.juegoTerminado = Herramientas.cargarImagen("trofeo-oro.png");
		this.calles = Herramientas.cargarImagen("calles.png");
		this.nivel = new Nivel(1);
		inicializadorDeCalles();
		inicializadorDeNinjas();
		inicializadorVidasNinjas();
		inicializadorDeCasas();
		inicializarObstaculos();
		this.r = new Random();
		this.numeroCasaGanadora = r.nextInt(40);
		this.NinjasMuertos = 0;
		this.CantNiveles = 3;
		this.puntaje = 0;
		this.entregas = 0;
		this.Sakura = new Sakura(308, 232);
		this.disparo=null;
		this.countTicks = 0;
		this.venta = Herramientas.cargarSonido("venta.wav");
		this.musica = Herramientas.cargarSonido("cancionfondo.wav");
		this.poder = Herramientas.cargarSonido("sonidopoder.wav");
		this.perder = Herramientas.cargarSonido("sonidoPerderVida.wav");
		this.ganar = Herramientas.cargarSonido("sonidoGanador.wav");
		this.musicaPerder = Herramientas.cargarSonido("cancionGameOver.wav");
		this.musicaGanar = Herramientas.cargarSonido("cancionGanadora.wav");
		
	}
	
	void colisionCasa() {
		casas[this.numeroCasaGanadora].dibujar(entorno);
		if (casas[this.numeroCasaGanadora].colisiona(Sakura)) {
			venta.loop(1);
			puntaje += 5;
			entregas ++;
			this.numeroCasaGanadora = r.nextInt(40);
		}
	}
	
	//inicializador de las calles
	void inicializadorDeCalles() {
		this.Lcalles= new Calles[8];
		int calleVx = 106; //estas serian las coordenadas de la calle vertical
		int calleVy = 0;
		int anchoVertical = 24;
		int largoVertical = 600;
		int calleHx = 0; //estas serian las coordenadas de la calle horizontal
		int calleHy = 74;
		int anchoHorizontal = 28;
		int largoHorizontal = 800;
		int offsetx = 190;
		int offsety = 144;
		for (int i = 0; i < 8; i++) {
			if (i < 4) {
			this.Lcalles[i] = new Calles(calleVx, calleVy, largoVertical, anchoVertical);
				calleVx = calleVx+offsetx; //el espacio entre calles en x es de 190
			}
			else {
				this.Lcalles[i] = new Calles(calleHx, calleHy, largoHorizontal, anchoHorizontal);
				calleHy = calleHy+offsety; //el espacio entre calles en y es de 144
				
			}
			 
		}
	}
	
	void inicializadorDeNinjas() {
		int cantNinjas = this.nivel.getCantNinjas()/2;
		double velocidad = this.nivel.getVelocidadNinjas();
		this.ninjaHorizontal = new Ninjas[cantNinjas];
		this.ninjaVertical = new Ninjas[cantNinjas];
		int y = 87;
		int x = 5;
		for (int i = 0; i < ninjaHorizontal.length; i++) {
			this.ninjaHorizontal[i] = new Ninjas(x, y,velocidad);
						y += 144;
		}
		x = 120;
		y = 5;
		for (int i = 0; i < ninjaVertical.length; i++) {
			this.ninjaVertical[i] = new Ninjas(x, y, velocidad);
			x += 190;
		}
	}
	
	void Shuriken() {
		if (this.nivel.getDisparoNinjas()) {
			for (int i = 0; i < ninjaHorizontal.length; i++ ) {
				if (ninjaHorizontal[i] !=null) {
					if (ninjaHorizontal[i].buscadorHorizontal(Sakura)) {
						if (this.shurikensH[i] == null) {
							this.shurikensH[i] = new Shurikens(ninjaHorizontal[i].getX(),ninjaHorizontal[i].getY());
						}
					}
					shurikensDesaparece();
				}
				if (ninjaVertical[i] != null) {
					if (ninjaVertical[i].buscadorVertical(Sakura)) {
						if (this.shurikensV[i] == null) {
							this.shurikensV[i] = new Shurikens(ninjaVertical[i].getX(),ninjaVertical[i].getY());
						}
					}
					shurikensDesaparece();
				}
			}
		}
	}
	void shurikensDisparador() {
		if (this.nivel.getDisparoNinjas()) {
			for (int i = 0; i < this.nivel.getCantNinjas(); i++ ) {
				if (this.shurikensH[i] != null) {
					this.shurikensH[i].disparadorHorizontal(entorno);
				}
				if (this.shurikensV[i] != null) {
					this.shurikensV[i].disparadorVertical(entorno);
				}
			}
		}
	}
	void shurikensDesaparece() {
		for (int i = 0; i < this.nivel.getCantNinjas(); i++ ) {
			if (this.shurikensH[i] != null) {
				if (this.shurikensH[i].colisionBordes()) {
					this.shurikensH[i] = null;
				}
			}
			if (this.shurikensV[i] != null) {
				if (this.shurikensV[i].colisionBordes()) {
					this.shurikensV[i] = null;
				}
			}
		}
	}
	
	boolean colisionShuriken() {
		if (this.nivel.getDisparoNinjas()) {
			for (int i = 0; i < this.nivel.getCantNinjas(); i++) {
				if (this.shurikensH[i]!= null) {
					if (this.Sakura.colisionShuriken(this.shurikensH[i])) {
						return true;
					}
				}
				if (this.shurikensV[i] != null) {
					if (this.Sakura.colisionShuriken(this.shurikensV[i])) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	void clavos() {
		if (this.nivel.getClavosNinjas()) {
			int tiempo = this.countTicks/40;
			for (int i = 0; i < ninjaHorizontal.length; i++ ) {
				if (ninjaHorizontal[i] !=null && clavosH[i] == null && ninjaVertical[i] != null && clavosV[i] == null) {
					if (tiempo%30 == 0 && tiempo != 0) {
						clavosH[i] = new Clavos(ninjaHorizontal[i].getX(), ninjaHorizontal[i].getY());
						clavosV[i] = new Clavos(ninjaVertical[i].getX(), ninjaVertical[i].getY());
					}
				}
			}
		}
	}
	
	void dibujadorClavos() {
		if (this.nivel.getClavosNinjas()) {
			for (int i = 0; i < clavosH.length; i++ ) {
				if (clavosH[i] != null && clavosV[i] != null) {
					clavosH[i].dibujar(entorno);
					clavosV[i].dibujar(entorno);
				}
			}
		}
	}
	
	boolean colisionClavos() {
		if (this.nivel.getClavosNinjas()) {
			for (int i = 0; i < clavosH.length; i++ ) {
				if (clavosH[i] != null && clavosV[i] != null) {
					if (Sakura.colisionClavos(clavosH[i]) || Sakura.colisionClavos(clavosV[i])) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	void inicializadorVidasNinjas() {
		
		//Reseteo los arrays
		this.vidaNinjaH = null;
		this.vidaNinjaV = null;
		// Calculo la cantidad igual que en inicializar ninjas 
		int cantNinjas = this.nivel.getCantNinjas()/2;  
		
		// Java inicializa por defecto los valores en 0 
		this.vidaNinjaH = new int[cantNinjas];
		this.vidaNinjaV = new int[cantNinjas];
		
	}
	
	void inicializadorDeCasas() {
		this.casas = new Casas [40];
		int x = 120, y = 30;
		for (int i = 0; i < 4; i++) {
			this.casas[i] = new Casas (x,y,30,30);
			x += 190;
		}
		y = 88;
		x = 70;
		for (int i = 4; i < 8; i++) {
			this.casas[i] = new Casas (x,y,30,30);
			x += 190;
		}
		y = 125;
		x = 117;
		for (int i = 8; i < 12; i++) {
			this.casas[i] = new Casas (x,y,30,30);
			x += 190;
		}
		y = 170;
		x = 115;
		for (int i = 12; i < 16; i++) {
			this.casas[i] = new Casas (x,y,30,30);
			x += 190;
		}
		y = 224;
		x = 70;
		for (int i = 16; i < 20; i++) {
			this.casas[i] = new Casas (x,y,30,30);
			x += 190;
		}
		y = 270;
		x = 120;
		for (int i = 20; i < 24; i++) {
			this.casas[i] = new Casas (x,y,30,30);
			x += 190;
		}
		y = 330;
		x = 115;
		for (int i = 24; i < 28; i++) {
			this.casas[i] = new Casas (x,y,30,30);
			x += 190;
		}
		y = 410;
		x = 117;
		for (int i = 28; i < 32; i++) {
			this.casas[i] = new Casas (x,y,30,30);
			x += 190;
		}
		y = 455;
		x = 115;
		for (int i = 32; i < 36; i++) {
			this.casas[i] = new Casas (x,y,30,30);
			x += 190;
		}
		y = 555;
		x = 114;
		for (int i = 36; i < 40; i++) {
			this.casas[i] = new Casas (x,y,30,30);
			x += 190;
		}
	}
	
	void inicializarObstaculos() {
		if (this.nivel.getDisparoNinjas()) {
			this.shurikensH = null;
			this.shurikensV = null;
			this.shurikensH = new Shurikens[this.nivel.getCantNinjas()];
			this.shurikensV = new Shurikens[this.nivel.getCantNinjas()];
		}
		if (this.nivel.getClavosNinjas()) {
			this.clavosH = null;
			this.clavosV = null;
			this.clavosH = new Clavos [this.nivel.getCantNinjas()];
			this.clavosV = new Clavos [this.nivel.getCantNinjas()];
		}
	}

	void saltoSakura() {
		if ((this.entorno.sePresiono(this.entorno.TECLA_ARRIBA) 
							|| this.entorno.estaPresionada(this.entorno.TECLA_ARRIBA )) && Sakura.moverSakura('S',this.Sakura,this.Lcalles)) {
			this.Sakura.salto();
		} else if ((this.entorno.sePresiono(this.entorno.TECLA_DERECHA) 
							|| this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) && Sakura.moverSakura('D',this.Sakura,this.Lcalles)) {
			this.Sakura.saltarDerecha();
		} else if ((this.entorno.sePresiono(this.entorno.TECLA_IZQUIERDA) 
							|| this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) && Sakura.moverSakura('I',this.Sakura,this.Lcalles)) {
			this.Sakura.saltarIzquierda();
		} else if ((this.entorno.sePresiono(this.entorno.TECLA_ABAJO) 
							|| this.entorno.estaPresionada(this.entorno.TECLA_ABAJO)) && Sakura.moverSakura('B',this.Sakura,this.Lcalles)) {
			this.Sakura.saltarAbajo();
		} else {
			this.Sakura.caer();
		}
	}
	
//	boolean colisionBordes() {
//		if (this.Sakura.getX() > 800 || this.Sakura.getX() < 0 || this.Sakura.getY() > 600 || this.Sakura.getY() < 0) {
//			return true;
//		}
//		return false;
//	}

	void dibujadorDeNinjas() {
		//dibuja ninjas horizontales
		for (int i = 0; i < ninjaHorizontal.length; i++) {
			if (this.ninjaHorizontal[i] != null) {
				this.ninjaHorizontal[i].dibujarse(entorno);
				this.ninjaHorizontal[i].moverseHorizontal();
			}
		}//dibuja ninjas verticales
		for (int i = 0; i < ninjaVertical.length; i++) {
			if (this.ninjaVertical[i] != null) {
				this.ninjaVertical[i].dibujarse(entorno);
				this.ninjaVertical[i].moverseVertical();
			}
		}
	}

	boolean colisionNinja() {
		for (int i = 0; i < this.ninjaHorizontal.length; i++) {
			if (this.ninjaHorizontal[i] != null) {
				if (this.Sakura.colisionNinja(this.ninjaHorizontal[i])) {
					return true;
				}
			}
			if (this.ninjaVertical[i] != null) {
				if (this.Sakura.colisionNinja(this.ninjaVertical[i])) {
					return true;
				}
			}
		}
		return false;
	}
	
	void disparoDeSakura() {
		if (this.entorno.sePresiono(this.entorno.TECLA_ESPACIO)) {
			if(this.disparo==null) {
				this.disparo = new Disparo(this.Sakura.getX(), this.Sakura.getY(), this.Sakura.getUltimaDireccion());
				this.poder.loop(1);
			}	
		}
		desaparecerDisparo();
	}

	boolean disparoColision() {
		for (int i = 0; i < this.ninjaHorizontal.length; i++) {
			if (this.ninjaHorizontal[i] != null) {
				if (this.disparo.colision(ninjaHorizontal[i])) {
					this.ninjaHorizontal[i] = null;
					this.NinjasMuertos ++;
					this.puntaje += 2;
					return true;
				}
			}
			if (this.ninjaVertical[i] != null) {
				if (this.disparo.colision(ninjaVertical[i])) {
					this.ninjaVertical[i] = null;
					this.NinjasMuertos ++;
					this.puntaje += 2;
					return true;
					}
				}
		}
		return false;
	}

	void desaparecerDisparo() {
		if(this.disparo!=null) {
			if(disparoColision()) {
				this.disparo=null;
				}
			else if (this.disparo.colisionBordes()) {
				this.disparo=null;
			}
		}
	}

	void gameOver() {
		this.perder.loop(0);
		this.entorno.dibujarImagen(gameover, 500, 300, 0);
		this.entorno.cambiarFont("Aharoni", 30, Color.RED);
		this.entorno.escribirTexto("PRESS ENTER FOR RESTART ", 300, 300);
		this.musicaPerder.loop(5);
	}
	
	void restartGame() {
		if (this.entorno.sePresiono(this.entorno.TECLA_ENTER)) {
			this.musicaGanar.stop();
			this.musicaPerder.stop();
			this.inicializarTodo();
			
		}
	}
	
	void comprobarEntregas() {
		if (this.entregas >= this.nivel.getCantEntregas()) {
			subirNivel();
		}
	}
	
	void subirNivel() {
		int nivelSuperado = this.nivel.getNivel(); 
		this.musica.stop();
		this.ganar.loop(0);
		this.musicaGanar.loop(5);
		this.entorno.dibujarRectangulo(500, 300, 1000, 600, 0, Color.BLACK);
		this.entorno.dibujarImagen(this.subirNivel, 250, 250, 0);
		this.entorno.cambiarFont("Aharoni", 80, Color.BLACK);
		this.entorno.escribirTexto(""+nivelSuperado, 230, 290);
		this.entorno.cambiarFont("Aharoni", 30, Color.BLUE);
		this.entorno.escribirTexto("¡FELICIDADES NIVEL " + nivelSuperado + " SUPERADO!", 455, 100);
		this.entorno.escribirTexto("Puntaje Alcanzado: "+ this.puntaje, 660, 200);
		this.entorno.escribirTexto("Ninjas Eliminados: "+ this.NinjasMuertos, 660, 250);
		Image imagenSakura = Herramientas.cargarImagen("Sakura.png");
		this.entorno.dibujarImagen(imagenSakura, 750, 300, 0);
		this.entorno.escribirTexto("PRESS ENTER FOR NEXT LEVEL ", 300, 570);
				
		
	}
	
	void juegoTerminado() {
		
		this.musica.stop();
		this.ganar.loop(0);
		this.musicaGanar.loop(5);
		this.entorno.dibujarRectangulo(500, 300, 1000, 600, 0, Color.BLACK);
		this.entorno.dibujarImagen(this.juegoTerminado, 350, 300, 0);
		
		this.entorno.cambiarFont("Aharoni", 30, Color.BLUE);
		this.entorno.escribirTexto("¡¡¡FELICIDADES JUEGO SUPERADO!!!", 280, 50);
		this.entorno.escribirTexto("Puntaje Final: "+ this.puntaje, 650, 200);
		this.entorno.escribirTexto("Ninjas Eliminados: "+ this.NinjasMuertos, 650, 250);
		Image imagenSakura = Herramientas.cargarImagen("Sakura.png");
		this.entorno.dibujarImagen(imagenSakura, 750, 300, 0);
		this.entorno.escribirTexto("PRESS ENTER FOR RESTART ", 300, 570);
		
	}
	
	void inicializarNivel() {
		if (this.entorno.sePresiono(this.entorno.TECLA_ENTER)) {
			this.nivel.subirNivel();
			this.musicaGanar.stop();
			this.gameover = Herramientas.cargarImagen("gameover.png");
			this.subirNivel = Herramientas.cargarImagen("trofeo-plata.png");
			this.juegoTerminado = Herramientas.cargarImagen("trofeo-oro.png");
			this.calles = Herramientas.cargarImagen("calles.png");
			inicializadorDeCalles();
			inicializadorDeNinjas();
			inicializadorVidasNinjas();
			inicializadorDeCasas();
			inicializarObstaculos();
			this.r = new Random();
			this.numeroCasaGanadora = r.nextInt(40);
			this.NinjasMuertos = 0;
			this.entregas = 0;
			this.Sakura = new Sakura(308, 232);
			this.disparo=null;
			this.countTicks = 0;
			this.venta = Herramientas.cargarSonido("venta.wav");
			this.musica = Herramientas.cargarSonido("cancionfondo.wav");
			this.poder = Herramientas.cargarSonido("sonidopoder.wav");
			this.perder = Herramientas.cargarSonido("sonidoPerderVida.wav");
			this.ganar = Herramientas.cargarSonido("sonidoGanador.wav");
			this.musicaPerder = Herramientas.cargarSonido("cancionGameOver.wav");
			this.musicaGanar = Herramientas.cargarSonido("cancionGanadora.wav");
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
