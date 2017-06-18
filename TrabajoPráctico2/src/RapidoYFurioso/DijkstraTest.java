package RapidoYFurioso;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class DijkstraTest {
	private Grafo grafo;
	private Dijkstra dijkstra;
	
	@Before
	public void crearGrafo() {
		grafo = new Grafo(6);
		grafo.agregarArista(0, 1, 2, true);
		grafo.agregarArista(0, 2, 3, true);
		grafo.agregarArista(0, 3, 1, false);
		grafo.agregarArista(1, 4, 2, false);
		grafo.agregarArista(2, 4, 4, true);
		grafo.agregarArista(2, 5, 4, false);
	}
	
//	@Test
//	public void agregarVecinosTest(){
//		dijkstra = new Dijkstra(grafo, 0, 4, 0);
//		dijkstra.agregarVecinos(dijkstra.inicio, 0);
//		
//		ArrayList<Arista> expected = new ArrayList<Arista>();
//		expected.add(new Arista(0,1,2,true));
//		expected.add(new Arista(0,2,3,true));
//	}
	
//	@Test
//	public void agregarVecinosTest(){
//		dijkstra = new Dijkstra(grafo, 0, 1, 1);
//		grafo.tomarArista(2,4).FueRecorrido();
//		
//		dijkstra.agregarVecinos(2, grafo.tomarArista(0, 2).getPeajesVisitados());
//
//		ArrayList<Arista> expected = new ArrayList<Arista>();
//		expected.add(new Arista(2,4,4,true));
//		expected.add(new Arista(2,5,4,false));
//		
//		assertEquals(expected, dijkstra.auxiliar);
//	}
	
	@Test
	public void NoRecorridoMenorPesoTest() {
		dijkstra = new Dijkstra(grafo, 0, 4, 0);
		
		dijkstra.auxiliar.add(new Arista(0,3,0,false));
		dijkstra.auxiliar.add(new Arista(0,1,3,true));
		dijkstra.auxiliar.add(new Arista(0,4,2,true));
		dijkstra.auxiliar.add(new Arista(0,2,1,true));
		dijkstra.auxiliar.get(0).recorrer();
		
		assertEquals(new Arista(0,2,1,true), dijkstra.noRecorridoConMenorPeso());
	}
	
	@Test
	public void buscarPorInicioTest(){
		dijkstra = new Dijkstra(grafo,0,0,0);
		Arista expected = new Arista(4,5,8,false);
		
		dijkstra.yaRecorridos.add(new Arista(1,2,3,true));
		dijkstra.yaRecorridos.add(new Arista(3,2,8,true));
		dijkstra.yaRecorridos.add(new Arista(4,2,7,false));
		dijkstra.yaRecorridos.add(new Arista(2,4,8,false));

		
		assertEquals(4, dijkstra.buscarPorInicio(expected));
	}
		
	@Test
	public void acumularDatosTest(){
		dijkstra = new Dijkstra(grafo, 0, 0 ,0);
		
		dijkstra.yaRecorridos.add(new Arista(1,2,4,true));
		Arista arista = new Arista(2,3,4,true);
		dijkstra.acumularDatos(arista, 1);
		
		assertEquals(8, arista.getPeso());
		assertEquals(2, arista.getPeajesVisitados());
	}
	
	@Test
	public void transformarAEnteroTest(){
		dijkstra = new Dijkstra(grafo,1,2,0);
		Arista aristaPrimera = new Arista(0,1,2,true);
		Arista aristaSegunda = new Arista(1,2,2,true);
		Arista solucion = new Arista(2,3,3,true);
		dijkstra.resultado = solucion;
		
		aristaPrimera.asignarAnteriorDe(aristaSegunda);
		aristaSegunda.asignarAnteriorDe(solucion);
		dijkstra.transformarAEntero();
		
		assertEquals("123", dijkstra.mostrarResultado());
	}
	
	@Test
	public void dijkstraTestPeaje(){
		Grafo grafo = instanciaGrafo1();
		
		Dijkstra s = new Dijkstra(grafo,0,4,1);
		Arista menor = s.resolver();
		
		String resultado = s.mostrarResultado();
		assertEquals("0134", resultado);
	}
	
	private Grafo instanciaGrafo1() {
		Grafo grafo = new Grafo(6);
		grafo.agregarArista(0, 1, 10, true);
		grafo.agregarArista(0, 2, 1, true);
		grafo.agregarArista(1, 3, 9, false);
		grafo.agregarArista(2, 3, 1, true);
		grafo.agregarArista(3, 4, 12, false);
		grafo.agregarArista(3, 5, 1, false);
		return grafo;
	}
}
