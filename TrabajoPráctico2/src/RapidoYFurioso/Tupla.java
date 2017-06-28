package RapidoYFurioso;

import java.io.Serializable;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Tupla implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Arista arista;
	private Coordinate coordenada;
	
	public Tupla(){
		arista = new Arista();
	}
	
	public Arista arista(){
		return arista;
	}
	
	public void crearCoordenada(Coordinate Coordenada){
		coordenada = Coordenada;
	}
	
	public Coordinate getCoordenada(){
		return coordenada;
	}
	
	
}
