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
	ArrayList<Arista> auxiliar = new ArrayList<Arista>();	// no privadas para testearlas
	int inicio, solucion, cantPeajes;
	private Arista resultado;
	private Grafo grafo;
	
	public Dijkstra(Grafo g, int i, int destino, int cantidadPeajes) {
		grafo = g;		inicio = i;		solucion = destino;		cantPeajes = cantidadPeajes;
		
		yaRecorridos.add(new Arista(i, i, 0));
	}
	
	void agregarVecinos(int indice, Integer peso){
		ArrayList<Arista> vecinos = grafo.getVecinos(indice);
		
		for(Arista arista: vecinos)
			if (!auxiliar.contains(arista) 
					&& arista.getPeajesVisitados() + peso <= cantPeajes)
				auxiliar.add(arista);
	}
	
	 Arista NoRecorridoConMenorPeso() {
		Arista referencia = new Arista();
		referencia.agregar(null, null, (int) Double.POSITIVE_INFINITY);

		for (Arista arista : auxiliar)
			if (!arista.fueRecorrido() 
					&& arista.getPeso() < referencia.getPeso())
				referencia = arista;

		return referencia;
	}
	
	void marcarPrincipal(Arista menor) {
		int indice = buscarPorInicio(menor);

		menor.yaRecorrido();
		acumularDatos(menor,indice);

		Arista suplente = new Arista(menor.getInicio(), menor.getDestino(),
				menor.getPeso());
		suplente.setPeajesRecorridos(menor.getPeajesVisitados());
		suplente.yaRecorrido();
		
		yaRecorridos.get(indice).asignarAnteriorDe(suplente);
		yaRecorridos.add(suplente);
	}
	
	private void acumularDatos(Arista arista, int indice) {
		if (arista.getInicio() != 0) {
			int peso = yaRecorridos.get(indice).getPeso() == null ? arista.getPeso()
					: arista.getPeso() + yaRecorridos.get(indice).getPeso();

			arista.setPeso(peso);
		}
		
		int peajes = yaRecorridos.get(indice).getPeajesVisitados() == 0 ? arista.getPeajesVisitados()
				: arista.getPeajesVisitados() + yaRecorridos.get(indice).getPeajesVisitados();

		arista.setPeajesRecorridos(peajes);
	}
	
	private int buscarPorInicio(Arista arista){
		int indice = -1;
		for (int i = 0; i < yaRecorridos.size(); i++) 
			if (yaRecorridos.get(i).getDestino() == arista.getInicio()){
				indice = i;
				break;
			}
		return indice;
	}
	
	public Arista resolver() {
		Arista menor = new Arista();
		menor.agregar(inicio, inicio, 0);

		while (menor.getPeso() != (int) Double.POSITIVE_INFINITY && menor.getDestino() != solucion) {
			agregarVecinos(menor.getDestino(), menor.getPeajesVisitados());
			
			menor = NoRecorridoConMenorPeso();

			marcarPrincipal(menor);
		}
		
		resultado = ResultadoEnRecorrido();
		transformarAEntero();
		return resultado;
	}
	
	private Arista ResultadoEnRecorrido(){
		Arista resultado = new Arista();
		for (int i = 0; i < yaRecorridos.size(); i++) 
			if (yaRecorridos.get(i).getDestino() == solucion)
				resultado = yaRecorridos.get(i);
		
		return resultado;
	}
	
	private void transformarAEntero() {
		Arista arista = resultado;
		while (arista.getAnterior() != null) {
			camino.addFirst(arista.getDestino());
			arista = arista.getAnterior();
		}
		camino.addFirst(yaRecorridos.get(0).getInicio());
	}
	
	public String mostrarResultado(){
		String resultado ="";
		for (Integer i: camino)
			resultado += (i);
		return resultado;
	}
}
