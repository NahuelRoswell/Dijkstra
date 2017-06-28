import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class GrafoTest
{
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void agregarFueraDeRangoTest() {
		Grafo grafo = new Grafo(4);
		grafo.agregarArista(1, 9, 3);
	}


	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void gradoInvalidoTest() {
		Grafo grafo = crearGrafo();
		grafo.getGrado(-1);
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void gradoExcedidoTest() {
		Grafo grafo = crearGrafo();
		grafo.getGrado(6);
	}
	
	@Test
	public void getGrado(){
		Grafo grafo = crearGrafo();
		
		assertEquals(2, grafo.getGrado(0));
		assertEquals(1, grafo.getGrado(1));
	}
	
	@Test
	public void tomarArista(){
		Grafo grafo = crearGrafo();
		
		Arista a= new Arista();
		a.agregar(0, 1, 3);
		
		assertEquals(a, grafo.tomarArista(0, 1));
	}
	
	@Test
	public void tomarAristaPeso(){
		Grafo grafo = crearGrafo();
		
		int peso = grafo.tomarArista(0, 1).getPeso();
		
		assertEquals(3, peso);
	}
	
	@Test
	public void tomarAristaPeajeTest(){
		Grafo grafo = crearGrafo();
		
		boolean peaje = grafo.tomarArista(0, 1).tienePeaje();
		
		assertEquals(true, peaje);
	}

	private Grafo crearGrafo() {
		Grafo grafo = new Grafo(6);
		grafo.agregarArista(0, 1, 3,true);
		grafo.agregarArista(0, 4, 3);
		grafo.agregarArista(1, 2, 3);
		grafo.agregarArista(2, 3, 3);
		grafo.agregarArista(3, 4, 3);

		return grafo;
	}
}
