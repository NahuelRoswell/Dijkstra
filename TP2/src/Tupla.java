import java.io.Serializable;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import Arista;

public class Tupla implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Arista arista;
	private Coordinate coordenada;
	private static int ids = 0;
	private int id;
	
	public Tupla(){
		arista = new Arista();
		id = ids;
		ids++;
	}
	
	public Arista arista(){
		return arista;
	}
	 
	public void crearCoordenada(Coordinate c){
		coordenada = new Coordinate(c.getLat(),c.getLon());
	}

	public Coordinate coordenada(){
		return coordenada;
	}
	
	public String getNombre(){
		return ""+id;
	}

}
