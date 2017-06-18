package RapidoYFurioso;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GrafoTest {
	Grafo grafo = new Grafo(5);
	
	@Before
	public void crearGrafo(){
		grafo.agregarArista(0, 1, 2, true);
		grafo.agregarArista(0, 2, 3, true);
		grafo.agregarArista(0, 3, 1, false);
		grafo.agregarArista(1, 4, 2, false);
		grafo.agregarArista(2, 4, 4, true);
	}
	
	@Test
	public void getVerticesTest(){
		assertEquals(5, grafo.getVertices());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void tomarIvalidoTest() {
		Grafo grafo = new Grafo(2);
		
		grafo.tomarArista(0, 3);
	}
	
	@Test
	public void agregarAristaTest() {
		Arista arista = new Arista(0, 1, 2, true);
		
		assertEquals(arista, grafo.tomarArista(0, 1));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void agregarAristaInvalidaTest(){
		grafo.agregarArista(0, 9, 1, true);
		grafo.agregarArista(-1, 2, 1, true);
	}
	
	@Test
	public void tomarVecinosNoRecorridosTest(){
		grafo.tomarArista(0, 3).recorrer();

		ArrayList<Arista> vecinos = grafo.tomarVecinosNoRecorridos(0);
		ArrayList<Arista> expected = new ArrayList<Arista>();
		expected.add(new Arista(0,1,2,true));
		expected.add(new Arista(0,2,3,true));
		
		assertEquals(expected, vecinos);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void tomarVecinosIndiceInvalidoTest(){
		ArrayList<Arista> vecinos = grafo.tomarVecinosNoRecorridos(7);
	}
	
	@Test
	public void getGradoTest(){
		assertEquals(3,grafo.getGrado(0));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getGradoInvalidoTest(){
		grafo.getGrado(9);
	}

}
