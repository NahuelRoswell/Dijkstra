package RapidoYFurioso;

import java.util.ArrayList;

public class Grafo {
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
