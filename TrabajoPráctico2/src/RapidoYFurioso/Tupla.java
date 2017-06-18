package RapidoYFurioso;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Tupla {
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
