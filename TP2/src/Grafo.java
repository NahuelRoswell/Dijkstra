import java.io.Serializable;
import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import RapidoYFurioso.Tupla;

public class Grafo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tupla[][] grafo;
	
	Grafo(int vertices){
		grafo = new Tupla[vertices][vertices];
		
		inicializar();
	}
		
	private void inicializar() {
		for(int i = 0; i < getVertices(); i++) 
			for (int j = 0; j < getVertices(); j++)
				if (grafo[i][j]==null)
					grafo[i][j]= new Tupla();
	}
	
	public void agregarVertice(Coordinate latitudLongitud) {
		int vertices = grafo.length;

		Tupla nueva[][] = new Tupla[vertices + 1][vertices + 1];
		for (int i = 0; i < vertices; ++i)
			for (int j = 0; j < vertices; ++j)
				nueva[i][j] = grafo[i][j];

		grafo = nueva;
		inicializar();
		grafo[vertices][vertices].crearCoordenada(latitudLongitud);
	}

	public void agregarArista(int inicio, int destino, int peso){
		chequearIndice(inicio, destino, "agregar Arista");
		
		grafo[inicio][destino].arista().agregar(inicio,destino, peso);
	}
	
	public void agregarArista(int inicio, int destino, int peso, boolean peaje){
		chequearIndice(inicio, destino, "agregar Arista");
		 
		grafo[inicio][destino].arista().agregar(inicio,destino, peso, peaje);
	}
	
	public Arista tomarArista(int inicio, int destino){
		chequearIndice(inicio,destino,"tomar Arista");
		
		return grafo[inicio][destino].arista();
	}
	
	public Coordinate tomarCoordenada(int inicio, int destino){
		chequearIndice(inicio,destino,"tomar coordenada");
		
		return grafo[inicio][destino].coordenada();
	}
	
	public int tamaño(){
		return grafo.length;
	}
	
	private void chequearIndice(int indice, int destino, String accion) {
		if(indice < 0 || destino < 0 || indice > getVertices()-1 || destino > getVertices()-1)
			throw new ArrayIndexOutOfBoundsException("el indice para " +accion +" esta fuera de rango! I =" +indice
					+" J = "+destino);
	}
	
	public ArrayList<Integer> getVecinosInt(int indice){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		
		for (int i = 0; i < grafo.length; i++)
			if (grafo[indice][i].arista().getDestino() != null 
				&& !grafo[indice][i].arista().fueRecorrido())
					ret.add(grafo[indice][i].arista().getDestino());

		return ret;
	}
	
	public ArrayList<Arista> getVecinos(int indice){
		ArrayList<Arista> ret = new ArrayList<Arista>();
		
		for (int i = 0; i < grafo.length; i++)
			if (grafo[indice][i].arista().getDestino() != null 
				&& !grafo[indice][i].arista().fueRecorrido())
					ret.add(grafo[indice][i].arista());

		return ret;
	}
	
	public int getVertices(){
		return grafo.length;
	}
	
	public int getGrado(int i){
		int vecinos = 0;
		for (int j = 0; j < getVertices(); j++) 
			if (grafo[i][j].arista().getDestino()!=null)
				vecinos++;
		
		return vecinos;
	}
}