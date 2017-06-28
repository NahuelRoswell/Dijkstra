import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class DijkstraTest {
	
	@Test
	public void dijkstraTestPeaje(){
		Grafo grafo = instanciaGrafo1();
		
		Dijkstra s = new Dijkstra(grafo,0,4, 1);
		Arista menor = s.resolver();
		
		String resultado = s.mostrarResultado();
		assertEquals("0134", resultado);
	}
	
	@Test
	public void dijkstraTestlibre(){
		Grafo grafo = instanciaGrafo1();
		
		Dijkstra s = new Dijkstra(grafo,0,4, 99);
		s.resolver();
		
		String resultado = s.mostrarResultado();
		assertEquals("0234",resultado);
	}
	
	@Test
	public void dijkstraTestLibre2(){
		Grafo grafo = instanciaGrafo2();
		
		Dijkstra s = new Dijkstra(grafo,0,7,0);
		s.resolver();
		
		String resultado = s.mostrarResultado();
		assertEquals("02357",resultado);
	}
	
	@Test
	public void agregarVecinosTest(){
		Grafo grafo = instanciaGrafo2();
		
		Dijkstra s = new Dijkstra(grafo,0,7,0);
		s.agregarVecinos(s.inicio, 0);

		ArrayList<Arista> aux = new ArrayList<Arista>();
		aux.add(new Arista(0, 1, 3));
		aux.add(new Arista(0, 2, 1));

		assertEquals(aux, s.auxiliar);
	}

	@Test
	public void tomarMenorTest() {
		Grafo grafo = instanciaGrafo2();

		Dijkstra s = new Dijkstra(grafo, 0, 7, 0);
		s.agregarVecinos(s.inicio, 0);

		assertEquals(s.NoRecorridoConMenorPeso(), new Arista(0, 2, 1));
	}

	@Test
	public void NoRecorridoConMenorPesoTest(){
		Grafo grafo = instanciaGrafo2();
		
		Dijkstra s = new Dijkstra(grafo,0,7,0);
		s.auxiliar.add(new Arista(1,2,4));
		s.auxiliar.add(new Arista(2,3,5));
		s.auxiliar.add(new Arista(1,3,1));
		s.auxiliar.add(new Arista(1,4,7));
				
		assertEquals(new Arista(1,3,1),s.NoRecorridoConMenorPeso());
	}

	private Grafo instanciaGrafo2() {
		Grafo grafo = new Grafo(8);
		grafo.agregarArista(0, 1, 3);
		grafo.agregarArista(0, 2, 1);
		grafo.agregarArista(1, 6, 5);
		grafo.agregarArista(1, 3, 1);
		grafo.agregarArista(2, 3, 1);
		grafo.agregarArista(2, 5, 5);
		grafo.agregarArista(3, 4, 4);
		grafo.agregarArista(3, 5, 1);
		grafo.agregarArista(4, 7, 1);
		grafo.agregarArista(5, 7, 1);
		grafo.agregarArista(6, 4, 2);
		return grafo;
	}

	private Grafo instanciaGrafo1() {
		Grafo grafo = new Grafo(6);
		grafo.agregarArista(0, 1, 10, true);
		grafo.agregarArista(0, 2, 1, true);
		grafo.agregarArista(1, 3, 9);
		grafo.agregarArista(2, 3, 1, true);
		grafo.agregarArista(3, 4, 12);
		grafo.agregarArista(3, 5, 1);
		return grafo;
	}
}
