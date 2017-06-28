package RapidoYFurioso;

import java.io.Serializable;
import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Grafo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Tupla[][] grafo;
	
	public Grafo(int vertices){
		grafo = new Tupla[vertices][vertices];
		inicializar();
	}
	
	private void inicializar(){
		for (int i = 0; i < getVertices(); i++) 
			for (int j = 0; j < getVertices() ; j++)
				if (grafo[i][j] == null)
					grafo[i][j] = new Tupla();
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
	
	public int getVertices(){
		return grafo.length;
	}
	
	public void agregarArista(int inicio, int destino, int peso, boolean peaje) {
		chequearIndice(inicio, destino, "agregar una arista");
		
		grafo[inicio][destino].arista().agregar(inicio, destino, peso, peaje);
	}
	
	public Arista tomarArista(int inicio, int destino){
		chequearIndice(inicio, destino, "tomar arista");
		
		return grafo[inicio][destino].arista();
	}
	
	public ArrayList<Arista> tomarVecinosNoRecorridos(int indice){
		chequearIndice(indice,indice, "tomar vecinos no recorridos ");
		ArrayList<Arista> ret = new ArrayList<Arista>();
		
		for (int i = 0; i < getVertices(); i++) 
			if (grafo[indice][i].arista().getDestino() != null && 
				!grafo[indice][i].arista().FueRecorrido())
					ret.add(grafo[indice][i].arista());
		
		return ret;
	}
	
	public int getGrado(int indice){
		chequearIndice(indice, indice, "verificar el grado ");
		
		int vecinos = 0;
		for (int j = 0; j < getVertices(); j++) 
			if (grafo[indice][j].arista().getDestino() != null)
				vecinos++;
		
		return vecinos;
	}

	private void chequearIndice(int inicio, int destino, String accion) {
		if (inicio < 0)
			throw new IllegalArgumentException("Se intentó " +accion +"con un inicio es inválido! inicio = " +inicio);
			
		if (destino > getVertices())
			throw new IllegalArgumentException("Se intentó " +accion +"con undestino es inválido! destino = " +destino);
	}
	
	void limpiarGrafo(){
		for (int i = 0; i < getVertices(); i++) 
			for (int j = 0; j < getVertices() ; j++)
				if (grafo[i][j] != null)
					grafo[i][j].arista().limpiar();
	}

}
