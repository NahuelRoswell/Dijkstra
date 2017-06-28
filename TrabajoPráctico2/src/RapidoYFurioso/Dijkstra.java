package RapidoYFurioso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Dijkstra implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Integer> camino = new LinkedList<Integer>();
	ArrayList<Arista> yaRecorridos = new ArrayList<Arista>();
	ArrayList<Arista> auxiliar = new ArrayList<Arista>();
	int inicio, destino, cantPeajes;
	Arista resultado;
	private Grafo grafo;

	public Dijkstra(Grafo Grafo, int Inicio, int Destino, int cantidadPeajes) {
		grafo = Grafo;
		inicio = Inicio;
		destino = Destino;
		cantPeajes = cantidadPeajes;
		
		yaRecorridos.add(new Arista(inicio,inicio,0,false));
	}

	public Arista resolver() {
		Arista menor = new Arista(inicio, inicio, 0, false);
		
		while (menor.getPeso() != Integer.MAX_VALUE
				&& menor.getDestino() != destino){
			agregarVecinos(menor.getDestino(), menor.getPeajesVisitados());
			
			menor = noRecorridoConMenorPeso();
			
			marcarPrincipal(menor);
		}
		resultado = menor;
		transformarAEntero();
		limpar();
		return menor;
	}

	private void limpar() {
		yaRecorridos.clear();
		auxiliar.clear();
		grafo.limpiarGrafo();
		yaRecorridos.add(new Arista(inicio,inicio,0,false));
	}

	void agregarVecinos(Integer indice, Integer peajesVisitados) {
		ArrayList<Arista> vecinos = grafo.tomarVecinosNoRecorridos(indice);
		
		for (Arista arista: vecinos)
			if (!auxiliar.contains(arista) &&
					arista.getPeajesVisitados() + peajesVisitados <= cantPeajes)
				auxiliar.add(arista);
	}

	Arista noRecorridoConMenorPeso() {
		Arista referencia = new Arista();
		referencia.agregar(inicio, inicio, Integer.MAX_VALUE, false);
		
		for (Arista arista : auxiliar)
			if (!arista.FueRecorrido() 
					&& arista.getPeso() < referencia.getPeso())
				referencia = arista;
		
		return referencia;
	}

	void marcarPrincipal(Arista menor) {
		int indice = buscarPorInicio(menor);
		
		menor.recorrer();
		acumularDatos(menor,indice);
		
		yaRecorridos.get(indice).asignarAnteriorDe(menor);
		yaRecorridos.add(menor);
	}

	int buscarPorInicio(Arista arista) {
		int indice = -1;
		 
		for (int i = 0; i < yaRecorridos.size(); i++) 
			if (yaRecorridos.get(i).getDestino() == arista.getInicio()){
				indice = i;
				break;
			}
		
		return indice;
	}
	
	void acumularDatos(Arista arista, int indice) {
		if (arista.getInicio() != 0){
			arista.acumularPesoCon(yaRecorridos.get(indice));
			arista.acumularPeajescon(yaRecorridos.get(indice));
		}
	}
	
	void transformarAEntero(){
		camino.clear();
		Arista arista = resultado; 
		while (arista.getAnterior() != null){
			camino.addFirst(arista.getDestino());
			arista = arista.getAnterior();
		}
		camino.addFirst(yaRecorridos.get(0).getInicio());
	}
	
	public String mostrarResultado(){
		String resultado = "";
		for (Integer i: camino)
			resultado += i;
		
		return resultado;
	}
	
	public int getDistanciaRecorrida(){  //testear
		if (resultado!=null)
			return resultado.getPeso();
		
		return 0;
	}
	
	public static void main(String[] args) {
		Grafo grafo = new Grafo(6);
		grafo.agregarArista(0, 1, 2, true);
		grafo.agregarArista(0, 2, 3, true);
		grafo.agregarArista(0, 3, 1, false);
		grafo.agregarArista(1, 4, 2, false);
		grafo.agregarArista(2, 4, 4, true);
		grafo.agregarArista(2, 5, 4, false);

		Dijkstra s = new Dijkstra(grafo, 0, 4,1);
		System.out.println(s.resolver());
		System.out.println(s.resolver());
	}
	

	
	
}
